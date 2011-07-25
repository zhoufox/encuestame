/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.encuestame.utils.web.QuestionAnswerBean;

/**
 * Question Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since 01/06/2009 15:22:10
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -3106607865655197340L;

    /**
     * Question name.
     */
    @JsonProperty(value = "question_name")
    private String questionName = new String("");

    /**
     *  Slug name.
     */
    @JsonProperty(value = "slug")
    private String slugName;

    /**
     * Hits.
     */
    @JsonProperty(value = "hits")
    private Long hits;

    /**
     *
     */
    @JsonProperty(value = "version")
    private String version;

    /**
     *
     */
    @JsonProperty(value = "pattern")
    private QuestionPatternBean pattern = new QuestionPatternBean();

    /**
     *
     */
    @JsonProperty(value = "state_id")
    private Long stateId;

    /**
     *
     */
    @JsonProperty(value = "id")
    private Long id;

    /**
     * User Id.
     */
    @JsonProperty(value = "uid")
    private Long userId;

    /**
     *
     */
    @JsonProperty(value = "list_answers")
    private List<QuestionAnswerBean> listAnswers = new ArrayList<QuestionAnswerBean>();

    /**
     * Constructor.
     */
    public QuestionBean() {
    }

    /**
     * Constructor Name.
     * @param name
     */
    public QuestionBean(final String name){
        this.questionName = name;
    }

    /**
     * @return the questionName
     */
    @JsonIgnore
    public final String getQuestionName() {
        return questionName;
    }

    /**
     * @param questionName
     *            the questionName to set
     */
    public final void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * @return the version
     */
    @JsonIgnore
    public final String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the pattern
     */
    @JsonIgnore
    public final QuestionPatternBean getPattern() {
        return pattern;
    }

    /**
     * @param pattern
     *            the pattern to set
     */
    public final void setPattern(final QuestionPatternBean pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the id
     */
    @JsonIgnore
    public final Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the listAnswers
     */
    @JsonIgnore
    public final List<QuestionAnswerBean> getListAnswers() {
        return listAnswers;
    }

    /**
     * @param listAnswers
     *            the listAnswers to set
     */
    public final void setListAnswers(final List<QuestionAnswerBean> listAnswers) {
        this.listAnswers = listAnswers;
    }

    /**
     * @return the stateId
     */
    @JsonIgnore
    public final Long getStateId() {
        return stateId;
    }

    /**
     * @param stateId
     *            the stateId to set
     */
    public final void setStateId(final Long stateId) {
        this.stateId = stateId;
    }

    /**
     * @return the userId
     */
    @JsonIgnore
    public final Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public final void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the slugName
     */
    @JsonIgnore
    public String getSlugName() {
        return slugName;
    }

    /**
     * @param slugName the slugName to set
     */
    public void setSlugName(final String slugName) {
        this.slugName = slugName;
    }



    /**
     * @return the hits
     */
    @JsonIgnore
    public Long getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(final Long hits) {
        this.hits = hits;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "QuestionBean [questionName=" + questionName + ", slugName="
                + slugName + ", version=" + version + ", pattern=" + pattern
                + ", stateId=" + stateId + ", id=" + id + ", userId=" + userId
                + ", listAnswers=" + listAnswers + "]";
    }
}