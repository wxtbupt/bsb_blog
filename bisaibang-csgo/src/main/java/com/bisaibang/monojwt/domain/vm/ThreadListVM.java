package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.forum.SingleThread;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Lynn on 2017/3/22.
 */
public class ThreadListVM {

    private List<SingleThread> topThreads;

    private Page<SingleThread> normalThreads;

    @Override
    public String toString() {
        return "ThreadListVM{" +
            "topThreads=" + topThreads +
            ", normalThreads=" + normalThreads +
            '}';
    }

    public List<SingleThread> getTopThreads() {
        return topThreads;
    }

    public void setTopThreads(List<SingleThread> topThreads) {
        this.topThreads = topThreads;
    }

    public Page<SingleThread> getNormalThreads() {
        return normalThreads;
    }

    public void setNormalThreads(Page<SingleThread> normalThreads) {
        this.normalThreads = normalThreads;
    }
}
