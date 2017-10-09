package com.bisaibang.monojwt.service.forum;


import com.bisaibang.monojwt.domain.forum.Post;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.repository.PostRepository;
import com.bisaibang.monojwt.repository.SingleThreadRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.ZonedDateTime;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;


@Service
public class MSPostService {

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SingleThreadRepository sTRepository;

    public ResponseEntity<?> createPost(Long threadid, Post data){
        data = msAuthority.postSQLInject(data);
        SingleThread t = sTRepository.findOne(threadid);
        Post p = Post.create(data.getTitle(),data.getContent(),data.getState(), t);
        p.setCreator(msAuthority.current());
        p.setAuthorName(msAuthority.current().getNickName());
        postRepository.save(p);
        t.incPostNumber();
        t.setEditDate(ZonedDateTime.now());
        sTRepository.save(t);
        return ResponseEntity.ok(message("post创建成功"));
    }
}
