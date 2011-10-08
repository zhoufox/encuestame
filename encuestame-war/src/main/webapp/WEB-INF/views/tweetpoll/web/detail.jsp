<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail");
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");
  dojo.require("encuestame.org.core.commons.stream.HashTagInfo");
  dojo.require("encuestame.org.core.comments.Comments");
  dojo.require("encuestame.org.core.commons.social.LinksPublished");
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer");
</script>
<div class="web-tweetpoll-detail ">
   <div class="question defaultSectionTitle">
        ${tweetpoll.questionBean.questionName}
   </div>
   <div class="web-tweetpoll-info">
        <div id="info" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail"
             <c:if test="${tweetpoll.limitVotesDate}">
                date="${tweetpoll.dateToLimit}"
            </c:if>
             completed="${tweetpoll.completed}"
             owner="${tweetpoll.ownerUsername}"
             tweetPollid="${tweetpoll.id}"></div>
   </div>
    <div class="web-tweetpoll-answer-wrapper">
        <div class="web-tweetpoll-answer-chart">
            <div id="chart" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail"
                 tweetPollid="${tweetpoll.id}"
                 completed="${tweetpoll.completed}"
                 username="${tweetpoll.ownerUsername}"></div>
        </div>
        <div class="web-tweetpoll-answer-answer">
            <table style="margin-top: 15px; width: 590px; float: right;"
                class="web-tweetpoll-answer-table" cellspacing="0">
                <thead>
                    <tr>
                        <th class="header-table-answer"></th>
                        <th class="header-table-answer">Link to Vote</th>
                        <th class="header-table-answer">Count</th>
                        <th class="header-table-answer">Percent</th>
                        <th class="header-table-answer"></th>
                    </tr>
                    <tr>
                        <th class="header-table-answer-emtpy"></th>
                        <th class="header-table-answer-emtpy"></th>
                        <th class="header-table-answer-emtpy"></th>
                        <th class="header-table-answer-emtpy"></th>
                        <th class="header-table-answer-emtpy"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${answers}" var="a">
                         <tr class="answer"
                              dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer"
                              aId="${a.answers.questionAnswerId}"
                              color="${a.answers.color}"
                              label="${a.answers.answer}"
                              owner="${tweetpoll.ownerUsername}"
                              completed="${tweetpoll.completed}"
                              url="${a.shortUrl}">
                         </tr>
                   </c:forEach>
                </tbody>
            </table>
        </div>
   </div>
   <div class="web-tweetpoll-social-links">
       <div dojoType="encuestame.org.core.commons.social.LinksPublished" itemId="${tweetpoll.id}" type="TWEETPOLL"></div>
   </div>
   <c:if test="${!empty hashtags}">
       <div class="web-tweetpoll-hashtags">
           <div class="titleTweetPollSection extraMargin">HashTag Refered</div>
               <c:forEach items="${hashtags}" var="h">
                       <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
               </c:forEach>
       </div>
   </c:if>
   <div class="web-tweetpoll-comments">
       <div class="titleTweetPollSection extraMargin">Comments</div>
      <div dojoType="encuestame.org.core.comments.Comments" type="tweetpoll" item_id="${tweetpoll.id}"></div>
   </div>
</div>
