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

package org.encuestame.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.imp.IFolder;
import org.encuestame.core.persistence.domain.CatEmails;
import org.encuestame.core.persistence.domain.Question;
import org.encuestame.core.persistence.domain.security.SecUser;
import org.encuestame.core.persistence.domain.survey.Poll;
import org.encuestame.core.persistence.domain.survey.PollFolder;
import org.encuestame.core.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitQuestionBean;
import org.springframework.stereotype.Service;

/**
 * Poll Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 01, 2010
 * @version $Id: $
 */
@Service
public class PollService extends AbstractSurveyService implements IPollService{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Create Poll.
     */
    public final void createPoll(final UnitPoll pollBean, final String currentUser) throws EnMeExpcetion{
        try {
            final Poll pollDomain = new Poll();
            final Question question = getQuestionDao().retrieveQuestionById(pollBean.getQuestionBean().getId());
            if (question == null){
                 throw new EnMeExpcetion("question not found");
            }
            pollDomain.setCreatedAt(pollBean.getCreationDate());
            pollDomain.setPollOwner(getUser(currentUser).getSecUser());
            pollDomain.setPollCompleted(Boolean.FALSE);
            pollDomain.setCreatedAt(new Date());
            pollDomain.setPollHash(MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500)));
            pollDomain.setQuestion(question);
            pollDomain.setCloseNotification(pollBean.getCloseNotification());
            pollDomain.setEndDate(pollBean.getFinishDate());
            pollDomain.setPublish(pollBean.getPublishPoll());
            pollDomain.setShowVotes(pollBean.getShowResultsPoll());
            this.getPollDao().saveOrUpdate(pollDomain);

        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * List Poll ByUser.
     * @param currentUser currentUser
     * @return unitPoll
     * @throws EnMeDomainNotFoundException
     */

    public List<UnitPoll> listPollByUser(final String currentUser) throws EnMeDomainNotFoundException{
        final List<UnitPoll> unitPoll = new ArrayList<UnitPoll>();
        final List<Poll> polls = getPollDao().findAllPollByUserId(getPrimaryUser(currentUser));
         for (Poll poll : polls) {
             unitPoll.add(ConvertDomainBean.convertPollDomainToBean(poll));
        }
        return unitPoll;
    }

    /**
     * List Poll by Question Keyword.
     * @param currentUser currentUser
     * @param keyword QuestionKeyword
     * @return {@link UnitPoll}
     */
    public List<UnitPoll> listPollbyQuestionKeyword(final String currentUser,
            final String keyword) {
        final List<Poll> polls = getPollDao().getPollsByQuestionKeyword(keyword);
        return ConvertDomainBean.convertSetToUnitPollBean(polls);
    }

    /**
     * Get Polls by Folder.
     * @param folder
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitPoll> getPollsByFolder(final UnitFolder folder, final String username) throws EnMeDomainNotFoundException{
        final SecUser secUser = getUser(username).getSecUser();
        final List<Poll> polls = getPollDao().getPollsByPollFolder(secUser, getPollFolder(folder.getId()));
        return ConvertDomainBean.convertSetToUnitPollBean(polls);
    }
    /**
     *
    */
    public void updateAnswersPoll( ){
    }

    public void updateQuestionPoll(UnitQuestionBean unitQuestionPoll)
            throws EnMeExpcetion {
        // TODO Auto-generated method stub

    }

    /**
     *
     */
    public String createUrlPoll(
            final String domain,
            final String hashUrl,
            final String currentUser) {
        StringBuffer urlBuffer = new StringBuffer(domain);
        urlBuffer.append("/".concat(currentUser));
        urlBuffer.append("/".concat(hashUrl));
        return urlBuffer.toString();
    }

    /**
     *
     */
    public void publicPollByList(String urlPoll, UnitLists emailList) {
        final List<CatEmails> emailsList = getEmailListsDao().findEmailsByListId(emailList.getId());
        if(emailList !=null){
                 for (CatEmails emails : emailsList) {
                   getServiceMail().send(emails.getEmail(),"New Poll", urlPoll);
                  }
         }
         else{
             log.warn("Not Found Emails in your EmailList");
        }
    }

    /**
     *
     * @param folderId
     * @return
     */
    public List<UnitPoll> getPollByFolderId(final Long folderId){
        return null;
    }

    /**
     * Retrieve Folder Poll.
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException exception
     */
    public List<UnitFolder> retrieveFolderPoll(final String username) throws EnMeDomainNotFoundException{
        final SecUser secUser = getUser(username).getSecUser();
        final List<IFolder> folders = getPollDao().getPollFolderBySecUser(secUser);
        return ConvertDomainBean.convertListToUniUnitFolder(folders);
    }

    /**
     * Create Poll Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public UnitFolder createPollFolder(final String folderName, final String username) throws EnMeDomainNotFoundException{
        final PollFolder pollFolder = new PollFolder();
        pollFolder.setUsers(getUser(username).getSecUser());
        pollFolder.setCreatedAt(new Date());
        pollFolder.setFolderName(folderName);
        this.getPollDao().saveOrUpdate(pollFolder);
        return ConvertDomainBean.convertFolderToBeanFolder(pollFolder);
    }

    /**
     * Update FolderName.
     * @param folderId folder id
     * @param newFolderName folder name
     * @param username username
     * @return {@link UnitFolder}
     * @throws EnMeDomainNotFoundException exception
     */
    public UnitFolder updateFolderName(final Long folderId,
            final String newFolderName,
            final String username) throws EnMeDomainNotFoundException{
        final PollFolder folder = this.getPollFolder(folderId);
        if(folder == null){
            throw new EnMeDomainNotFoundException("poll folder not found");
        } else {
            folder.setFolderName(newFolderName);
            getPollDao().saveOrUpdate(folder);
        }
        return ConvertDomainBean.convertFolderToBeanFolder(folder);
    }

    /**
     * Get Poll Folder.
     * @param id
     * @return
     */
    private PollFolder getPollFolder(final Long id){
        return this.getPollDao().getPollFolderById(id);
    }

    /**
     * Remove PollFolder.
     * @param folderId
     * @throws EnMeDomainNotFoundException
     */
    public void removePollFolder(final Long folderId) throws EnMeDomainNotFoundException{
        final PollFolder folder = this.getPollFolder(folderId);
        if(folder != null){
            getPollDao().delete(folder);
        } else {
            throw new EnMeDomainNotFoundException("poll folder not found");
        }
    }

}
