# Triangle ImageView
[![Developer](https://img.shields.io/badge/developer-sina%20dalvand-orange)](https://github.com/sinadalvand)
[![Lisense](https://img.shields.io/badge/License-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://api.bintray.com/packages/sinadalvand/maven/TriangleImageView/images/download.svg?version=1.0.1) ](https://bintray.com/sinadalvand/maven/TriangleImageView/1.0.1/link)

<img src="https://github.com/sinadalvand/TriangleImageView/blob/master/art/logo.png" width="70"/>

Triangle ImageView with custom corner radius and animation switch


<img src="https://github.com/sinadalvand/TriangleImageView/blob/master/art/preview.gif" width="250"/>


## Gradle & Maven
```
implementation 'ir.sinadalvand.libs:TriangleImageView:1.0.1'
```
```
<dependency>
	<groupId>ir.sinadalvand.libs</groupId>
	<artifactId>TriangleImageView</artifactId>
	<version>1.0.1</version>
	<type>pom</type>
</dependency>
```

for set image you can use normal method that you used before and also compatible whit Glide or Picasso

### set image radius :
by using this method you can set image corner radius 
but if you want to system do this m don't set any positive value
```
triangleImageView.setRadius(@Px float radius)
```

### For transform to Triangle or Rect with Animation:
by using this , can toggle imageView between Rect and Triangle by animation
```
triangleImageView.Animate()
```


### For transform to Triangle or Rect without Animation:
manual decide witch one to set on image view 
1- TriangleImageMode.COLLAPSED for Triangle mode
2- TriangleImageMode.EXPANDED for Rect mode

```
triangleImageView.setMode(TriangleImageMode mode)
```


### Set Transformation percent :
we call it morph percent , that make you able to decide how much progress between two mode
this method can use for collapsing layout that give you value to make user change transformation
```
triangleImageView.setMorph(float morph)
```



# License

    Copyright 2019 Sina Dalvand

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.















