package com.accenture.jive.mastodonbackend.controllers.mappers;

import com.accenture.jive.mastodonbackend.controllers.dtos.PostDtoOutput;
import com.accenture.jive.mastodonbackend.persistence.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "guid", target = "uuid")
    @Mapping(source = "post.city.name", target = "name")
    @Mapping(source = "timestampMastodonPosted", target = "mastodonDate")
    @Mapping(source = "postLink", target = "mastodonUrl")
    PostDtoOutput toDto(Post post);
    List<PostDtoOutput> toDtos(List<Post> posts);

}
