package com.xy.community.cache;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java","c++","c","c#","python","php","javascript","css","html","html5","node.js","golang","shell","bash","asp.net","ruby","less","objective-c","typescript","lua"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","spring mvc","spring boot","mybatis","mybatis-plus","struts","hibernate","ibatis","laravel","express","django","flask","koa","jquery","vue","bootstrap","easyui","angular","react","layui","semanticui","elementui"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","缓存 tomcat","unix","hadoop","windows-server","负载均衡"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql memcached","sqlserver","postgresql","sqlite"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","gitee","eclipse","myeclipse","vim","maven","svn","visual-studio-code","sublime-text","xcode intellij-idea","visual-studio","atom","emacs","textmate","hg"));
        tagDTOS.add(tool);

        TagDTO other =new TagDTO();
        other.setCategoryName("其他");
        other.setTags(Arrays.asList("其他","test","avatar_url","lombok","jrebel","yaml","login","快捷键"));
        tagDTOS.add(other);
        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        //二维数组两层内容循环出来，压平flatMap
//        tagDTOS.stream().map(tag->tag.getCategoryName());
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalidTag = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalidTag;
    }
}
