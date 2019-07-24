package com.baizhi.service;

import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private MyUtil myUtil;

    @Override
    @Transactional
    public void updateDownPath(Chapter chapter) {
        chapterDAO.updateChapterDownloadPath(chapter);
    }

    @Override
    public Map<String, Object> queryAll(Integer pageNum,Integer pageSize,String albumId) {
        return myUtil.queryAllForJqgrid(pageNum,pageSize,ChapterDAO.class,albumId);
    }

    @Override
    @Transactional
    public String addChapter(Chapter chapter) {
        String s = UUID.randomUUID().toString();
        chapter.setId(s);
        chapterDAO.insert(chapter);
        Integer count = chapterDAO.selectCount(chapter.getAlbumId());
        Album album = new Album();
        album.setId(chapter.getAlbumId());
        album.setCount(count);
        albumService.modifyAlbum(album,null);
        return s;
    }

    @Override
    @Transactional
    public void removeChapter(String id, HttpSession session) {
        myUtil.remove(ChapterDAO.class, id, Chapter.class, "getDownPath", "uploadFile", session);
    }
}
