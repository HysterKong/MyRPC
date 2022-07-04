package com.example.myrpc4.server;

import com.example.myrpc4.pojo.Blog;

public interface BlogService {
    Blog getBlogById(Integer id);
}
