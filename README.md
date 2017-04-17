# TextureViewVideoScaler
Very lightweight helper class to resize the video in texture view to fit center or crop center

Because TextureView doesn't support with video scaling like the ExoPlayer library and many develop still use TextureView to display the video. This library already provided the code for video scaling, so you don't need to write that boilerplate code anymore. Just use this library


Installation
===============================

Maven
```
<dependency>
  <groupId>com.akexorcist</groupId>
  <artifactId>textureviewvideoscaler</artifactId>
  <version>0.9.9</version>
</dependency>
```


Gradle
```
compile 'com.akexorcist:textureviewvideoscaler:0.9.9'
```


Usage
===============================
When you want to do the video scaling in your TextureView, just call the ```getVideoScaleMatrix(...)``` method 
```java
float viewWidth = ...TextureView's width...
float viewHeight = ...TextureView's height...
float videoWidth = ...Video's width...
float videoHeight = ...Video's height...
//int scaleType = VideoScaler.FILL;
//int scaleType = VideoScaler.CROP_CENTER;
int scaleType = VideoScaler.FIT_CENTER;
Matrix matrix = VideoScaler.getVideoScaleMatrix(viewWidth, viewHeight, videoWidth, videoHeight, scaleType);
textureView.setTransform(matrix);
```

This static method will result the result as Matrix class that you can set directly to TextureView (or customize something in Matrix class before use it)



Licence
===========================
Copyright 2017 Akexorcist

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
