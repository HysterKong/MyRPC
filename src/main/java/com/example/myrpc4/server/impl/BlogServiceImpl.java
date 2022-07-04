package com.example.myrpc4.server.impl;

import com.example.myrpc4.pojo.Blog;
import com.example.myrpc4.server.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").userId(22).build();
        System.out.println("客户端查询了: " + id + " 的博客");
        return blog;
    }
}
