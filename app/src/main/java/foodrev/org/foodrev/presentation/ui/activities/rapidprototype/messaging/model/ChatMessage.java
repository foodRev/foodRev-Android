/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model;

import java.util.Date;

public class ChatMessage {

    private String text;
    private String name;
    private String photoUrl;
    private long timestamp;
    private String imageUrl;
    private String channelName;

    public static final long NO_TIMESTAMP = -1;

    public ChatMessage() {
    }

    public ChatMessage(String text, String name, String photoUrl, String currentChannel) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.timestamp = new Date().getTime();
        this.channelName = currentChannel;
    }

    public ChatMessage(String text, String name, String photoUrl, String currentChannel, String imageUrl) {
        this(text, name, photoUrl, currentChannel);
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(long newTimestamp){
        this.timestamp = newTimestamp;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getChannelName(){
        return channelName;
    }

}
