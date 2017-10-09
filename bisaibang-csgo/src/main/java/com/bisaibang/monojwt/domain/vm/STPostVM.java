package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.forum.Post;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Lynn on 2017/3/22.
 */
public class STPostVM {

    private SingleThread singleThread;

    private Page<Post> posts;

    public SingleThread getSingleThread() {
        return singleThread;
    }

    public void setSingleThread(SingleThread singleThread) {
        this.singleThread = singleThread;
    }

    public Page<Post> getPosts() {
        return posts;
    }

    public void setPosts(Page<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "STPostVM{" +
            "singleThread=" + singleThread +
            ", posts=" + posts +
            '}';
    }

    public static STPostVM create(SingleThread singleThread, Page<Post> posts){
        STPostVM stPostVM =new STPostVM();
        stPostVM.setPosts(posts);
        stPostVM.setSingleThread(singleThread);
        return stPostVM;

    }


}
