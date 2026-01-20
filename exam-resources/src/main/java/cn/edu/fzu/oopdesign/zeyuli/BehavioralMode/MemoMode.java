package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 备忘录模式
 *
 * @author : 李泽聿
 * @since : 2026:01:19 15:51
 */
public class MemoMode {
    public static void main() {
        VersionControl versionControl = new VersionControl();
        versionControl.addCommit(new Commit("add a new file", "canvas1"), "v1.0");
        versionControl.addCommit(new Commit("add a new file", "canvas2"), "v1.1");
        versionControl.addCommit(new Commit("delete a bug , but appear more bug", "canvas3"), "v1.2");

        Commit revert = versionControl.revert(String.valueOf("add a new file".hashCode() + "canvas2".hashCode()));
        revert.show();
    }
}

@Data
@AllArgsConstructor
@Slf4j
class Commit {
    private String content;
    private String canvas;

    @Override
    public int hashCode() {
        return content.hashCode() + canvas.hashCode();
    }

    public void show(){
        log.info("commit: {}", this);
    }
}

@Data
@AllArgsConstructor
class Git {
    private Commit commit;
    private String version;
}

class VersionControl {
    private final Map<String, Git> gitMap = new HashMap<>();

    public void addCommit(Commit commit, String version) {
        gitMap.put(String.valueOf(commit.hashCode()), new Git(commit, version));
    }

    public Commit revert(String key) {
        return gitMap.get(key).getCommit();
    }
}