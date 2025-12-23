package cn.edu.fzu.oopdesign.zeyuli.controller;

import cn.edu.fzu.oopdesign.zeyuli.enm.StatusCodeEnum;
import cn.edu.fzu.oopdesign.zeyuli.model.*;
import cn.edu.fzu.oopdesign.zeyuli.service.GameService;
import cn.edu.fzu.oopdesign.zeyuli.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private RedisUtils redisUtils;

    // Redis channel prefix
    private static final String GAME_CHANNEL_PREFIX = "game:channel:";

    /**
     * 主持人新建一个比赛
     */
    @PostMapping("/create")
    public Result<Map<String, String>> createGame(@RequestBody CreateGameRequest request) {
        String token = gameService.createGame(request);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

    /**
     * 主持人同步比赛信息
     */
    @PostMapping("/sync")
    public Result<Void> syncGame(@RequestBody SyncGameRequest request) {
        boolean success = gameService.syncGame(request);
        if (success) {
            return Result.success();
        } else {
            return Result.error(StatusCodeEnum.FAILED_TO_SYNC_GANE_INFO);
        }
    }

    /**
     * 用户查询比赛列表
     */
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getGameList() {
        List<Map<String, Object>> gameList = gameService.getGameList();
        return Result.success(gameList);
    }

    /**
     * 用户观看比赛，返回流式信息
     */
    @GetMapping(value = "/view", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter viewGame(@RequestParam String uuid) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        // Get game info
        GameInfo gameInfo = gameService.getGameInfo(uuid);
        if (gameInfo == null) {
            try {
                emitter.send(SseEmitter.event().name("error").data("Game not found"));
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
            return emitter;
        }

        // 获取 所有往届赛事
        List<GameEvent> events = gameService.getGameEvents(uuid);
        
        // 先发送所有过去的事件
        for (GameEvent event : events) {
            try {
                emitter.send(event);
            } catch (IOException e) {
                emitter.completeWithError(e);
                return emitter;
            }
        }

        // 为新事件创建一个 Redis 消息监听器
        // 为了简化，我们将使用轮询代替 Redis 的发布/订阅监听器
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                // Get latest events (just the last one for simplicity)
                List<GameEvent> latestEvents = gameService.getGameEvents(uuid);
                if (!latestEvents.isEmpty()) {
                    GameEvent latestEvent = latestEvents.get(0);
                    emitter.send(latestEvent);
                }
            } catch (IOException e) {
                executor.shutdown();
                emitter.completeWithError(e);
            }
        }, 1, 1, TimeUnit.SECONDS);

        // Handle connection close
        emitter.onCompletion(() -> executor.shutdown());
        emitter.onTimeout(() -> executor.shutdown());
        emitter.onError((e) -> executor.shutdown());

        return emitter;
    }
}