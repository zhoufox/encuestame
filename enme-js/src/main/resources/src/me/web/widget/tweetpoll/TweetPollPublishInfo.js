/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetPoll
 *  @namespace Widgets
 *  @class TweetPollPublishInfo
 */
define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollPublishItemStatus",
         "me/core/enme",
         "dijit/registry",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetpollPublish.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollPublishItemStatus,
                _ENME,
                registry,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          // Template string.
            templateString: template,

          /**
           * The list of social accounts.
           */
          _socialAccounts: [],

          /**
           * I18N Message.
           */
          i18nMessage: {
            button_finish: _ENME.getMessage("button_finish", "Close")
          },

          /**
           * Store all TweetPoll widget data
           * @param tweetPollWidget
           */
          tweetPollWidget: null,

          /**
           *
           */
          _inProcess: false,

          /**
           * Post create.
           */
          postCreate: function() {
              var button = registry.byId( this._close );
                  button.onClick = dojo.hitch( this, function( event ) {
                     dojo.publish("/encuestame/dialog/close");
                     document.location.href = _ENME.config("contextPath") + "/user/tweetpoll/list";
              });
          },

          /**
           * Set social account array.
           */
          setListOfSocialAccounts: function( accounts ) {
              this._socialAccounts = accounts;
          },

          /**
           * Initialize widget.
           */
          initialize: function( type ) {
              this._inProcess = true;
              if ( typeof type === "undefined") {
                  this._showProcessingMessage();
              } else if ( "scheduled" ) {
                  this._showProcessingMessage( type, _ENME.getMessage("schedulded_inprocess_status", "Your tweetpoll is beeing scheduled, please wait ...") );
              }
          },

          /**
           * Display proccessing message.
           */
          _showProcessingMessage: function( message_text ) {
            dojo.empty( this._message );
              var message = dojo.doc.createElement("div");
              dojo.addClass( this._message, "defaultDisplayBlock");
              dojo.removeClass( this._message, "defaultDisplayHide");
              message.innerHTML = message_text || _ENME.getMessage("pubication_inprocess_status");
              this._message.appendChild( message );
          },

          /**
           * Hidde the processing message.
           */
          _hideProcessingMessage: function() {
            dojo.empty( this._message );
              dojo.removeClass( this._message, "defaultDisplayBlock");
              dojo.addClass( this._message, "defaultDisplayHide");
              dojo.empty( this._message );
          },

          /**
           *
           * @method
           */
          processScheduledFinalStep: function( items ) {
             if ( items ) {
                  this._hideProcessingMessage();
                  dojo.empty( this._container );
                  dojo.empty( this._message );
                  dojo.forEach( items, dojo.hitch( this, function( data ) {
                      try {
                          data.scheduled_date = moment( data.scheduled_date ).format("MM-DD-YYYY hh:mm:ss");
                          data.social_account_id =  data.social_bean.id;
                          data.picture_url =  data.social_bean.picture_url;
                          data.status_tweet = "SCHEDULED";
                          var row = this._buildTweetProcessView( data );
                          if ( row ) {
                              this._container.appendChild( row );
                          }
                       }  catch ( error ) {
                          console.error("scheduled row ", error );
                       }
                 }) );
                 dojo.removeClass( this._closeWrapper, "hidden");
              } else {
                  this.errorMesage("data tweet process is empty");
              }
          },

          /**
           * Process date published.
           * @param socialPublish
           */
          process: function( socialPublish ) {
              if ( socialPublish ) {
                  this._hideProcessingMessage();
                  dojo.empty( this._container );
                  dojo.empty( this._message );
                  dojo.forEach( socialPublish,
                          dojo.hitch( this, function( tweet ) {
                              var row = this._buildTweetProcessView( tweet );
                              if ( row ) {
                                  this._container.appendChild( row );
                              }
                 }) );
                 dojo.removeClass( this._closeWrapper, "hidden");
              } else {
                  this.errorMesage("data tweet process is empty");
              }
          },

          /**
           * Search by id the complete info for selected social account.
           */
          _getSocialAccountWidget: function( id ) {

              //This._socialAccounts
              var selected = null;
              dojo.forEach( this._socialAccounts,
                      dojo.hitch( this, function( account ) {
                          if ( account.id == id ) {
                              selected = account;
                          }
               }) );
              return selected;
          },

          /**
           * Build tweet process view.
           */
          _buildTweetProcessView: function( data ) {
                return this._createStatusTweet( data );
          },

          /**
           * Create status tweet.
           */
          _createStatusTweet: function( data ) {
              var widget = new TweetPollPublishItemStatus(
                      {
                          data: data,
                          tweetPollWidget: this.tweetPollWidget, // Important, transfer the main widget reference
                          socialAccount: this._getSocialAccountWidget( data.social_account_id )
                      });
              return widget.domNode;
          }
    });
});
