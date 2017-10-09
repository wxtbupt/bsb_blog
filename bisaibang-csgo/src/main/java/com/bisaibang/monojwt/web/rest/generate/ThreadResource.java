package com.bisaibang.monojwt.web.rest.generate;

import com.bisaibang.monojwt.domain.forum.SingleThread;
import com.codahale.metrics.annotation.Timed;

import com.bisaibang.monojwt.repository.SingleThreadRepository;
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
 * REST controller for managing SingleThread.
 */
@RestController
@RequestMapping("/api")
public class ThreadResource {

    private final Logger log = LoggerFactory.getLogger(ThreadResource.class);

    private static final String ENTITY_NAME = "thread";

    private final SingleThreadRepository singleThreadRepository;

    public ThreadResource(SingleThreadRepository singleThreadRepository) {
        this.singleThreadRepository = singleThreadRepository;
    }

    /**
     * POST  /threads : Create a new singleThread.
     *
     * @param singleThread the singleThread to create
     * @return the ResponseEntity with status 201 (Created) and with body the new singleThread, or with status 400 (Bad Request) if the singleThread has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/threads")
    @Timed
    public ResponseEntity<SingleThread> createThread(@RequestBody SingleThread singleThread) throws URISyntaxException {
        log.debug("REST request to save SingleThread : {}", singleThread);
        if (singleThread.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new singleThread cannot already have an ID")).body(null);
        }
        SingleThread result = singleThreadRepository.save(singleThread);
        return ResponseEntity.created(new URI("/api/threads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /threads : Updates an existing singleThread.
     *
     * @param singleThread the singleThread to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated singleThread,
     * or with status 400 (Bad Request) if the singleThread is not valid,
     * or with status 500 (Internal Server Error) if the singleThread couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/threads")
    @Timed
    public ResponseEntity<SingleThread> updateThread(@RequestBody SingleThread singleThread) throws URISyntaxException {
        log.debug("REST request to update SingleThread : {}", singleThread);
        if (singleThread.getId() == null) {
            return createThread(singleThread);
        }
        SingleThread result = singleThreadRepository.save(singleThread);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, singleThread.getId().toString()))
            .body(result);
    }

    /**
     * GET  /threads : get all the threads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of threads in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/threads")
    @Timed
    public ResponseEntity<List<SingleThread>> getAllThreads(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Threads");
        Page<SingleThread> page = singleThreadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/threads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /threads/:id : get the "id" thread.
     *
     * @param id the id of the thread to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thread, or with status 404 (Not Found)
     */
    @GetMapping("/threads/{id}")
    @Timed
    public ResponseEntity<SingleThread> getThread(@PathVariable Long id) {
        log.debug("REST request to get SingleThread : {}", id);
        SingleThread singleThread = singleThreadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(singleThread));
    }

    /**
     * DELETE  /threads/:id : delete the "id" thread.
     *
     * @param id the id of the thread to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/threads/{id}")
    @Timed
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) {
        log.debug("REST request to delete SingleThread : {}", id);
        singleThreadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
