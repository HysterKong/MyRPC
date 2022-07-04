package com.example.myrpc2.server;

import com.example.myrpc2.pojo.Blog;

public interface BlogService {
    Blog getBlogById(Integer id);
}
