package com.bisaibang.monojwt.service.article;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.repository.ArticleRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.util.QiniuUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.ZonedDateTime;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

/**
 * Created by ccy on 2017/2/5.
 */
@Service
public class MSArticleService {

    @Autowired
    private MSAuthority mSAuthority;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MSAuthority msAuthority;

    public ResponseEntity<?> createArticle(Article article){
        article = mSAuthority.articleSQLInject(article);
        Article art = Article.create(article);
        art.setCreator(mSAuthority.current());
        art.setAuthorName(mSAuthority.current().getNickName());
        articleRepository.save(art);
        return ResponseEntity.ok(message("article创建成功"));
    }

    public ResponseEntity<?> editArticle(Long articleid, Article article){
        Article data =  msAuthority.articleSQLInject(article);
        return articleRepository.findOneById(articleid)
           .map(art -> {
               art.setName(data.getName());
               art.setThumbnailUrl(data.getThumbnailUrl());
               art.setIntroduction(data.getIntroduction());
               art.setTitle(data.getTitle());
               art.setContent(data.getContent());
               art.setType(data.getType());
               art.setState(data.getState());
               art.setEditDate(ZonedDateTime.now());
               articleRepository.save(art);
               return ResponseEntity.ok(message("article编辑成功"));
           })
           .orElseGet(() -> ResponseEntity.ok(message("不存在此article")));
    }

    public ResponseEntity<?> deleteArticle(Long id){
        Article a = articleRepository.findOne(id);
        a.deleteArticle();
        articleRepository.save(a);
        return ResponseEntity.ok(message("article删除成功"));
    }



    public ResponseEntity<?> getAvatarUploadToken(String key) {
        QiniuUtil imgupload = new QiniuUtil();
        imgupload.setBucketName("minisite-one");
        imgupload.setKey(key);
        return ResponseEntity.ok(message(imgupload.getUpToken(key)));
    }

    public ResponseEntity<?> updatePersonAvatarURL(String key, Long id) {
        QiniuUtil imgupload = new QiniuUtil();
        imgupload.setBucketName("minisite-one");
        Article a = articleRepository.findOne(id);
        a.setContent("https://msone.bisaibang.com/" + key);
        articleRepository.save(a);
        return ResponseEntity.ok(message("成功"));
    }
}
