/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.persistence.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TweetPoll Switch.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 12, 2010 10:31:45 PM
 * @version $Id:$
 */

@Entity
@Table(name = "tweetpoll_switch")
public class TweetPollSwitch {

    private Long switchId;

    private String codeTweet;

    private TweetPoll tweetPoll;

    private QuestionsAnswers answers;

    /**
     * @return the switchId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweetpoll_switch_id", unique = true, nullable = false)
    public Long getSwitchId() {
        return switchId;
    }

    /**
     * @param switchId
     *            the switchId to set
     */
    public void setSwitchId(Long switchId) {
        this.switchId = switchId;
    }

    /**
     * @return the codeTweet
     */
    @Column(name = "tweet_code", nullable = false, unique = true)
    public String getCodeTweet() {
        return codeTweet;
    }

    /**
     * @param codeTweet
     *            the codeTweet to set
     */
    public void setCodeTweet(String codeTweet) {
        this.codeTweet = codeTweet;
    }

    /**
     * @return the tweetPoll
     */

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tweet_poll_id", nullable = false)
    public TweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll
     *            the tweetPoll to set
     */
    public void setTweetPoll(TweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
     * @return the answers
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "q_answer_id", nullable = false)
    public QuestionsAnswers getAnswers() {
        return answers;
    }

    /**
     * @param answers
     *            the answers to set
     */
    public void setAnswers(QuestionsAnswers answers) {
        this.answers = answers;
    }
}