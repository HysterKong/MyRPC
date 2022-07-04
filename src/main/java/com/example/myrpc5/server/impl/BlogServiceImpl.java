package com.example.myrpc5.server.impl;

import com.example.myrpc5.pojo.Blog;
import com.example.myrpc5.server.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").userId(22).build();
        System.out.println("客户端查询了: " + id + " 的博客");
        return blog;
    }
}
