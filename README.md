# HttpQueryBuilder
Http request builder and cache response

Wraper for <a href="https://github.com/AsyncHttpClient/async-http-client">AsyncHttpClient</a>

```java
// extends BaseQuery
public class Query extends BaseQuery<Query> {}

// Query to server
new Query().init("http://url", StatusResult.TYPE.OBJECT)
                .addParam("limit", LIMIT)
                .addParam("page", page)
                .setCache(true, Query.FIVE_MIN)
                .getResult(this);
```
StatusResult.TYPE need if you override StatusResult with your own blackjack
