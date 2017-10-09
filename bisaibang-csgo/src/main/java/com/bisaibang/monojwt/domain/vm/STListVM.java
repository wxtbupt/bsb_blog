package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.forum.SingleThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Lynn on 2017/3/22.
 */
public class STListVM {

    private List<SingleThread> topThreads;

    private Page<SingleThread> normalThreads;

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

    @Override
    public String toString() {
        return "STListVM{" +
                "topThreads=" + topThreads +
                ", normalThreads=" + normalThreads +
                '}';
    }
}
