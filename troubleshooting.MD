# 목록

## 1. 로컬에서 이상 없던 /post/{postId} 경로에서 jar 실행 시 오류 발생

>template might not exist or might not be accessible by any of the configured template resolvers
---

```java
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    ...

    @GetMapping("/{postId}")
    public String postSingle(@PathVariable Long postId, 
    Model model, HttpServletRequest request,
    @SessionAttribute(required = false) UserSessionData userSessionData) {
        String referer = request.getHeader("Referer");

        boolean isCountUp = referer == null || (!referer.contains("edit") && !referer.contains("new") && !referer.contains("post"));

        PostResponseDto post = postService.findPost(postId,isCountUp);

        CommentRequestDto commentRequestDto = getCommentRequestDto(postId, userSessionData);

        model.addAttribute("post",post);
        model.addAttribute("commentForm",commentRequestDto);

        return "/post/postSingle"; //이부분에서 문제 발생
    }
}
```

## 원인
---
## 1. templates폴더는 src/main/resoureces/templates에 위치한다
> ## spring.thymeleaf.prefix=classpath:templates/

## 2. 따라서 상기의 리턴되는 템플릿은 templates//post/postSingle이 되어 경로에 오류가 발생한다

## 3. 로컬에서는 인텔리제이가 적절히 처리해주지만 빌드 후 jar 배포시에는 처리가 되지않는다


## 해결 방안

## 1. 템플릿 리턴 시 기본값 설정을 준수한다


```java
return "post/postSingle";
```

## 2. /post/postSingle 같은 구조를 유지하고자 한다면 설정을 하기로 바꾼다

> ## spring.thymeleaf.prefix=classpath:/templates
