/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.search.QueryBuilder;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagRanking; 
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * {@link HashTag} Dao.
 * 
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 25, 2010 5:30:46 PM
 * @version Id:
 */
@Repository("hashTagDao")
public class HashTagDao extends AbstractHibernateDaoSupport implements IHashTagDao {

    /**
     * The min size of hashtag to be displayed on cloud service.
     */
    private final static Long MIN_SIZE_CLOUD = 11L;

    /**
     *
     * @param sessionFactory
     */
    @Autowired
    public HashTagDao(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
 
    /**
     * Get HashTag By Name.
     *
     * @param hashTag
     * @return
     * @throws HibernateException
     */

    public HashTag getHashTagByName(final String hashTag)
            throws HibernateException {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(HashTag.class);
        criteria.add(Restrictions.eq("hashTag", hashTag));
        @SuppressWarnings("unchecked")
        final List<HashTag> results = (List<HashTag>) getHibernateTemplate().findByCriteria(
                criteria);
        if (results.size() >= 1) {
            return (HashTag) results.get(0); // TODO: it's possible repeated HashTags?
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IHashTagDao#getHashTags(java.lang.Integer,
     * java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<HashTag> getHashTags(
            final Integer maxResults,
            final Integer start,
            final String tagCriteria) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(HashTag.class);
        // TODO: please replace "hashTagsCloud" by ENUM.
        if (tagCriteria.equals("hashTagsCloud")) {
            criteria.add(Restrictions.gt("size", MIN_SIZE_CLOUD));
            // TODO: date?
            // criteria.add(Restrictions.gl("updatedDate",
            // getCurrentdMidnightDate()));
            criteria.addOrder(Order.desc("size"));
            criteria.addOrder(Order.asc("hashTag"));
        } else {
            criteria.addOrder(Order.desc("hits"));
            criteria.addOrder(Order.asc("hashTag"));
        }
        return (List<HashTag>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IHashTagDao#getListHashTagsByKeyword(java
     * .lang.String, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<HashTag> getListHashTagsByKeyword(
            final String keyword,
            final Integer maxResults,
            final Long[] excludes) {
        log.info("keyword " + keyword);
        List<HashTag> searchResult = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    @SuppressWarnings("deprecation")
                    public Object doInHibernate(org.hibernate.Session session) {
                        final Criteria criteria = session.createCriteria(HashTag.class);
                        // limit results
                        if (maxResults != null) {
                            criteria.setMaxResults(maxResults.intValue());
                        }
                        if (excludes != null && excludes.length > 0) {
                            for (int i = 0; i < excludes.length; i++) {
                                log.debug("excluding hashtag... " + excludes[i]);
                                criteria.add(Restrictions.ne("hashTagId",
                                        excludes[i]));
                            }
                        }
                        final QueryBuilder<HashTag> query = new QueryBuilder<>(getSessionFactory());
                        List<HashTag> results = query.build(criteria,
                                keyword,
                                maxResults,
                                0,
                                new String[] { "hashTag"},
                                "hashTag",
                                HashTag.class);
                        return results;
                    }
                });
        return searchResult;
    }
  
    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IHashTagDao#getHashTagRankStats(org.encuestame
     * .persistence.domain.HashTag)
     */
    //TODO:MIGRATION
    @SuppressWarnings("unchecked")
    public List<HashTagRanking> getHashTagRankStats(final Date maxDate) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(HashTagRanking.class);
        criteria.add(Restrictions.eq("rankingDate", maxDate));
        criteria.addOrder(Order.desc("average"));
        return (List<HashTagRanking>) getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IHashTagDao#getHashTagRankingLastPosition
     * (java.util.Date)
     */
    public List<HashTagRanking> getHashTagRankingLastPosition(final Date maxDate) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(HashTagRanking.class);
        criteria.add(Restrictions.not(Restrictions.eq("rankingDate", maxDate)));
        criteria.addOrder(Order.desc("average"));
        @SuppressWarnings("unchecked")
        List<HashTagRanking> results = (List<HashTagRanking>) getHibernateTemplate().findByCriteria(
                criteria);
        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IHashTagDao#getMaxHashTagRankingDate()
     */
    public Date getMaxHashTagRankingDate() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(HashTagRanking.class);
        criteria.setProjection(Projections.max("rankingDate"));
        @SuppressWarnings("unchecked")
        List<HashTagRanking> results = (List<HashTagRanking>) getHibernateTemplate().findByCriteria(criteria);
        return (Date) (results.get(0) == null ? new Date() : results.get(0));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IHashTagDao#getHashTagRankStatsById(java
     * .lang.Long)
     */
    public HashTagRanking getHashTagRankStatsById(final Long hashTagRankId)
            throws HibernateException {
        return (HashTagRanking) getHibernateTemplate().get(
                HashTagRanking.class, hashTagRankId);
    }

    /**
     * Get max-min tag frecuency.
     *
     * @param tag
     * @param filter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getMaxMinTagFrecuency() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(HashTag.class);
        ProjectionList projectList = Projections.projectionList();
        projectList.add(Projections.max("hits"));
        projectList.add(Projections.min("hits"));
        criteria.setProjection(projectList);
        return (List<Object[]>) getHibernateTemplate().findByCriteria(criteria);
    }
}
