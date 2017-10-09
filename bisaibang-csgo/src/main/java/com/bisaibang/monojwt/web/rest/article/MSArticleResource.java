package com.bisaibang.monojwt.web.rest.article;

import com.bisaibang.monojwt.domain.article.Article;
import com.bisaibang.monojwt.domain.people.User;
import com.bisaibang.monojwt.repository.ArticleRepository;
import com.bisaibang.monojwt.security.MSAuthority;
import com.bisaibang.monojwt.service.article.MSArticleService;
import com.bisaibang.monojwt.service.util.QiniuUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.bisaibang.monojwt.domain.util.ResponseMessage.message;

@RestController
@RequestMapping("/api")
public class MSArticleResource {

    private final Logger log = LoggerFactory.getLogger(MSArticleResource.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MSAuthority msAuthority;

    @Autowired
    private MSArticleService msArticleService;


    /**
     * 创建一篇article
     */
    @RequestMapping(value = "/ms-article/create",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createArticle(@RequestBody Article article) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msArticleService.createArticle(article);
    }


    /**
     * 编辑article
     */
    @RequestMapping(value = "/ms-article/edit/article/{articleid}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> editArticle(@PathVariable Long articleid, @RequestBody Article article) throws URISyntaxException {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msArticleService.editArticle(articleid, article);
    }


    /**
     * 删除article by creator
     */
    @PostMapping(path = "/ms-article/delete/article/{articleid}",
        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleid) throws URISyntaxException {
        if (!msAuthority.isCreator(articleid)){
            return ResponseEntity.ok(message("权限不足"));
        }
        return msArticleService.deleteArticle(articleid);
    }


    /**
     * 删除article by admin
     */
    @PostMapping(path = "/ms-article/admin/delete/article/{id}",
        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> deleteArticleByAdmin(@PathVariable Long id) throws URISyntaxException {
        if (!msAuthority.isAdmin()){
            return ResponseEntity.ok(message("权限不足"));
        }
        return msArticleService.deleteArticle(id);
    }


    /**
     * get all article
     */
    @GetMapping("/ms-article/get-all-article")
    @Timed
    public ResponseEntity<?> getAllArticle(@Valid Pageable pageable) throws URISyntaxException {
        return ResponseEntity.ok(articleRepository.findAllByTypeOrderByIdDesc("normal", pageable));
    }


    /**
     * get article by id
     */
    @GetMapping("/ms-article/get-article/{articleid}")
    @Timed
    public ResponseEntity<?> getArticleById(@PathVariable Long articleid) throws URISyntaxException {
        return articleRepository.findOneByIdAndType(articleid, "normal")
            .map(article -> {
                return ResponseEntity.ok(article);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * qiniuyun
     */
    @RequestMapping(value = "/ms-article/{key}/get-pic-upload-token",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getUploadToken(@PathVariable String key) {
        return msArticleService.getAvatarUploadToken(key);
    }


    /**
     * qiniuyun
     */
    @RequestMapping(value = "/ms-article/update-pic-url/article/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateArticleImageURL(@Valid @RequestBody String key, @PathVariable Long id) {
        if (!msAuthority.isAdmin()) {
            return ResponseEntity.ok(message("权限不足"));
        }
        return msArticleService.updatePersonAvatarURL(key, id);
    }

}
