
<%--
Copyright (c) 2011, salesforce.com, inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided
that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the
following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
the following disclaimer in the documentation and/or other materials provided with the distribution.

Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
--%>

canvas-upload
=============
1) Download repository to local directory
2) Create new Heroku app
3) Push repository to heroku
4) Create connected app on salesforce
   a) Choose 'Signed Request' method for authorization
   b) Use the following URL for canvas app https://<heroku app url>/uploadPage
   c) Choose to show Canvas app on VisualForce Page
   d) Create new visualforce page with following code
      <apex:page standardController="<Object Controller>">

          <apex:canvasApp applicationName="FileUploadCanvas" scrolling="yes" parameters="{sObjectId:'{!<ObjectName>.Id}'}"/>
 
      </apex:page>
      
      Change 'Object Controller' and 'Object Name' to match object to which you want to enable file upload
   e) Modify the layout to include the visualforce page snippet.
   
5) Copy Consumer Secret from connected app
6) Add the following config to heroku app
       heroku config:set CANVAS_CONSUMER_SECRET=<Whatever key you copied from connected app>
       
       
  
      
      
