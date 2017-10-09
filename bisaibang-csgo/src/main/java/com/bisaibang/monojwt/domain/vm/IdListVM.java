package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.forum.SingleThread;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by weiwensangsang on 2017/3/22.
 */
public class IdListVM {

    private List<Long> list;

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "IdListVM{" +
                "list=" + list +
                '}';
    }
}
