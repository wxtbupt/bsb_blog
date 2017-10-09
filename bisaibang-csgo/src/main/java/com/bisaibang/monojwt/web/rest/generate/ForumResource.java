package com.bisaibang.monojwt.web.rest.generate;

import com.codahale.metrics.annotation.Timed;
import com.bisaibang.monojwt.domain.forum.Forum;

import com.bisaibang.monojwt.repository.ForumRepository;
import com.bisaibang.monojwt.web.rest.generate.util.HeaderUtil;
import com.bisaibang.monojwt.web.rest.generate.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Forum.
 */
@RestController
@RequestMapping("/api")
public class ForumResource {

    private final Logger log = LoggerFactory.getLogger(ForumResource.class);

    private static final String ENTITY_NAME = "forum";

    private final ForumRepository forumRepository;

    public ForumResource(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * POST  /forums : Create a new forum.
     *
     * @param forum the forum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new forum, or with status 400 (Bad Request) if the forum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forums")
    @Timed
    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) throws URISyntaxException {
        log.debug("REST request to save Forum : {}", forum);
        if (forum.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new forum cannot already have an ID")).body(null);
        }
        Forum result = forumRepository.save(forum);
        return ResponseEntity.created(new URI("/api/forums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forums : Updates an existing forum.
     *
     * @param forum the forum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated forum,
     * or with status 400 (Bad Request) if the forum is not valid,
     * or with status 500 (Internal Server Error) if the forum couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forums")
    @Timed
    public ResponseEntity<Forum> updateForum(@RequestBody Forum forum) throws URISyntaxException {
        log.debug("REST request to update Forum : {}", forum);
        if (forum.getId() == null) {
            return createForum(forum);
        }
        Forum result = forumRepository.save(forum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, forum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forums : get all the forums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of forums in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/forums")
    @Timed
    public ResponseEntity<List<Forum>> getAllForums(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Forums");
        Page<Forum> page = forumRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /forums/:id : get the "id" forum.
     *
     * @param id the id of the forum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the forum, or with status 404 (Not Found)
     */
    @GetMapping("/forums/{id}")
    @Timed
    public ResponseEntity<Forum> getForum(@PathVariable Long id) {
        log.debug("REST request to get Forum : {}", id);
        Forum forum = forumRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(forum));
    }

    /**
     * DELETE  /forums/:id : delete the "id" forum.
     *
     * @param id the id of the forum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forums/{id}")
    @Timed
    public ResponseEntity<Void> deleteForum(@PathVariable Long id) {
        log.debug("REST request to delete Forum : {}", id);
        forumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
