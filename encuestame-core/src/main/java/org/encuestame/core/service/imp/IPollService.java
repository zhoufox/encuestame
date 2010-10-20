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
package org.encuestame.core.service.imp;

import java.util.List;

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitQuestionBean;

/**
 * Poll Service Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since May 16, 2010
 * @version $Id: $
 */
public interface IPollService extends IMasterSurveyService{

    /**
     *	Create Poll
     * @param pollBean
     * @param currentUser
     * @throws Exception
     */
  void createPoll(final UnitPoll pollBean, final String currentUser) throws Exception;

  /**
   * List Poll by Question.
   * @param currentUser currentUser
   * @param keyword Question keyword
   * @return
   */
  List<UnitPoll> listPollbyQuestionKeyword(final String currentUser, final String keyword);

  /**
   * List Poll By User.
   * @param currentUser currentUser
   * @return
 * @throws EnMeDomainNotFoundException
   */
  List<UnitPoll> listPollByUser(final String currentUser) throws EnMeDomainNotFoundException;

      /**
       * Update Question Poll.
       * @param unitQuestionPoll
       * @throws EnMeExpcetion exception
       */
  void updateQuestionPoll(final UnitQuestionBean unitQuestionPoll) throws EnMeExpcetion;

  /**
   * Create Url Poll.
   * @param domain
   * @param hashUrl
   * @param currentUser
   * @return
   */
  String createUrlPoll(final String domain, final String hashUrl, final String currentUser);

  /**
   * Public Poll by List.
   * @param urlPoll
   * @param emailList
   */
  void publicPollByList(final String urlPoll , final UnitLists emailList);

  /**
   * Remove PollFolder.
   * @param folderId
   * @throws EnMeDomainNotFoundException
   */
  void removePollFolder(final Long folderId) throws EnMeDomainNotFoundException;

  /**
   * Create Poll Folder.
   * @param folderName
   * @param username
   * @return
 * @throws EnMeDomainNotFoundException
   */
  UnitFolder createPollFolder(final String folderName, final String username) throws EnMeDomainNotFoundException;

  /**
   * Update FolderName.
   * @param folderId
   * @param newFolderName
   * @param username
   * @return
   * @throws EnMeDomainNotFoundException
   */
  public UnitFolder updateFolderName(final Long folderId,
          final String newFolderName,
          final String username) throws EnMeDomainNotFoundException;

  /**
   * Retrieve Folder Poll.
   * @param username
   * @return
   * @throws EnMeDomainNotFoundException exception
   */
  List<UnitFolder> retrieveFolderPoll(final String username) throws EnMeDomainNotFoundException;

  /**
   * Get Polls by Folder.
   * @param folder
   * @param username
   * @return
   * @throws EnMeDomainNotFoundException
   */
  List<UnitPoll> getPollsByFolder(final UnitFolder folder, final String username) throws EnMeDomainNotFoundException;

}