package cn.edu.fzu.oopdesign.zeyuli.service;

import cn.edu.fzu.oopdesign.zeyuli.model.*;
import cn.edu.fzu.oopdesign.zeyuli.utils.JwtUtils;
import cn.edu.fzu.oopdesign.zeyuli.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 *
 * @author : 李泽聿
 * @since : 2025-12-23 15:23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final RedisUtils redisUtils;

    private final JwtUtils jwtUtils;

    private final ObjectMapper objectMapper;

    // Redis key前缀
    private static final String GAME_PREFIX = "game:";
    private static final String GAME_LIST = "games:list";
    private static final String GAME_CHANNEL_PREFIX = "game:channel:";
    private static final String GAME_EVENTS_PREFIX = "game:events:";

    /**
     * 创建比赛
     *
     * @param request ：请求对象
     * @return : java.lang.String
     * @author : 李泽聿
     * @since : 2025-12-23 15:25
     */
    public String createGame(CreateGameRequest request) {
        // 生成唯一的比赛ID
        String gameId = UUID.randomUUID().toString().replace("-", "");

        // 创建GameInfo对象
        GameInfo gameInfo = new GameInfo();
        gameInfo.setUuid(gameId);
        gameInfo.setTitle(request.getTitle());
        gameInfo.setDescription(request.getDescription());
        gameInfo.setStartTime(request.getStartTime());
        gameInfo.setMaster(request.getMaster());
        gameInfo.setGuest(request.getGuest());
        gameInfo.setMasterScore(0);
        gameInfo.setGuestScore(0);

        // 保存到Redis
        redisUtils.set(GAME_PREFIX + gameId, gameInfo);

        // 将比赛添加到列表中
        Map<String, Object> gameListItem = new HashMap<>();
        gameListItem.put("name", request.getTitle());
        gameListItem.put("uuid", gameId);
        redisUtils.hSet(GAME_LIST, gameId, gameListItem);

        // 生成初始比萨事件进行直播
        GameEvent initEvent = new GameEvent();
        initEvent.setTeam("init");
        List<String> members = new ArrayList<>();
        members.add(request.getMaster().getName() + "|" + request.getMaster().getLogo());
        members.add(request.getGuest().getName() + "|" + request.getGuest().getLogo());
        initEvent.setMembers(members);
        initEvent.setAction("init");
        initEvent.setScore(0);
        initEvent.setTime("00:00:00");

        // 将初始事件保存到Redis列表
        redisUtils.lPush(GAME_EVENTS_PREFIX + gameId, initEvent);

        // 生成token
        return jwtUtils.generateToken(gameId);
    }

    /**
     * 同步比赛信息
     *
     * @param request ：请求对象
     * @return : boolean
     * @author : 李泽聿
     * @since : 2025-12-23 15:24
     */
    public boolean syncGame(SyncGameRequest request) {
        // 验证令牌
        if (!jwtUtils.validateToken(request.getToken())) {
            log.info("token 错误{}", request.getToken());
            return false;
        }

        String gameId = jwtUtils.getGameIdFromToken(request.getToken());
        if (gameId == null) {
            log.info("gameId 不存在");
            return false;
        }

        // 从Redis获取游戏信息
        GameInfo gameInfo = redisUtils.get(GAME_PREFIX + gameId, GameInfo.class);
        if (gameInfo == null) {
            log.info("比赛不存在");
            return false;
        }

        // 更新比赛得分
        if ("master".equals(request.getTeam())) {
            gameInfo.setMasterScore(gameInfo.getMasterScore() + request.getScore());
        } else if ("guest".equals(request.getTeam())) {
            gameInfo.setGuestScore(gameInfo.getGuestScore() + request.getScore());
        }

        // 保存已更新的比赛信息
        redisUtils.set(GAME_PREFIX + gameId, gameInfo);

        // 创建比赛事件
        GameEvent event = new GameEvent();
        event.setTeam(request.getTeam());
        event.setMembers(request.getMembers());
        event.setAction(request.getAction());
        event.setScore("master".equals(request.getTeam()) ? gameInfo.getMasterScore() : gameInfo.getGuestScore());
        event.setTime(request.getTime());

        // 将事件保存到Redis列表
        redisUtils.lPush(GAME_EVENTS_PREFIX + gameId, event);

        // 将事件发布到Redis频道进行直播
        redisUtils.publish(GAME_CHANNEL_PREFIX + gameId, event);

        return true;
    }

    /**
     * 获取所有比赛的列表
     *
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author : 李泽聿
     * @since : 2025-12-23 15:24
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getGameList() {
        // 从Redis hash获取所有游戏
        Set<Object> gameIds = redisUtils.hKeys(GAME_LIST);
        List<Map<String, Object>> gameList = new ArrayList<>();

        for (Object gameId : gameIds) {
            Map<String, Object> gameItem = redisUtils.hGet(GAME_LIST, gameId.toString(), Map.class);
            if (gameItem != null) {
                gameList.add(gameItem);
            }
        }

        return gameList;
    }

    /**
     * 获取指定比赛的详细信息
     *
     * @param gameId : 比赛ID
     * @return : cn.edu.fzu.oopdesign.zeyuli.model.GameInfo
     * @author : 李泽聿
     * @since : 2025-12-23 15:22
     */
    public GameInfo getGameInfo(String gameId) {
        return redisUtils.get(GAME_PREFIX + gameId, GameInfo.class);
    }

    /**
     * 获取指定比赛的活动列表
     *
     * @param gameId : 比赛ID
     * @return : java.util.List<cn.edu.fzu.oopdesign.zeyuli.model.GameEvent>
     * @author : 李泽聿
     * @since : 2025-12-23 15:23
     */
    public List<GameEvent> getGameEvents(String gameId) {
        // 从Redis列表获取所有活动
        return redisUtils.lRange(GAME_EVENTS_PREFIX + gameId, 0, -1)
                .stream()
                .map(obj -> (GameEvent) obj)
                .collect(java.util.stream.Collectors.toList());
    }

    public void sendEvent(SseEmitter emitter, GameEvent event) throws IOException {
        // 1. 对象 → UTF-8 字节
        byte[] utf8Bytes = objectMapper.writeValueAsBytes(event);
        // 2. UTF-8 字节 → Java 字符串（内容正确）
        String json = new String(utf8Bytes, StandardCharsets.UTF_8);
        // 3. 发送
        emitter.send(SseEmitter.event()
                .name("message")
                .data(json));
    }
}