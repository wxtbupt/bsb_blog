package com.bisaibang.monojwt.service.forum;


import com.bisaibang.monojwt.domain.forum.Forum;
import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.bisaibang.monojwt.domain.vm.STListVM;
import com.bisaibang.monojwt.repository.ForumRepository;
import com.bisaibang.monojwt.repository.PostRepository;
import com.bisaibang.monojwt.repository.SingleThreadRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.ManyToOne;
import java.util.List;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by ccy on 2017/2/5.
 */
@Service
public class MSSTService {

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private SingleThreadRepository sTRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ForumRepository forumRepository;


    public ResponseEntity<?> createThread(Long forumid, SingleThread thread) {
        thread = msAuthority.singleThreadSQLInject(thread);
        Forum forum = forumRepository.findOne(forumid);
        SingleThread thr = SingleThread.create(forum, thread);
        thr.setCreator(msAuthority.current());
        thr.setAuthorName(msAuthority.current().getNickName());
        return ResponseEntity.ok(sTRepository.save(thr));
    }

    public ResponseEntity<?> markThread(Long threadid) {
        SingleThread sT = sTRepository.findOne(threadid);
        sT.markSingleThread();
        sTRepository.save(sT);
        return ResponseEntity.ok(message("成功置顶"));
    }

    public ResponseEntity<?> revertMarkThread(Long threadid) {
        SingleThread sT = sTRepository.findOne(threadid);
        sT.revertMarkSingleThread();
        sTRepository.save(sT);
        return ResponseEntity.ok(message("成功回退"));
    }

    public ResponseEntity<?> batchDelete(List<Long> idList){
        for(Long id :idList){
            SingleThread t = sTRepository.findOne(id);
            t.deleteSingleThread();
            sTRepository.save(t);
        }
        return ResponseEntity.ok(message("成功批量删除"));
    }


}
