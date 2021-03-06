# LGUtilsK
Kotlin library with some utils extensions

## Getting Started

This library has some utils write in Kotlin for Android Development.

## How to Use

Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

Add the dependency

```
dependencies {
	        compile 'com.github.luangs7:LGUtilsK:latest-version'
	}
```

## Example

As a extensions, all you need to do is instantiate some class and use some function.
```
editText.shakeView()
editText.addPhoneMask()

val string = "Teste"
print(string.md5())
```

## Summary

[Click here](https://github.com/luangs7/LGUtilsK/blob/master/lgutilsk/src/main/java/br/com/luan2/lgutilsk/utils/Summary.md) to check the summary to see all functions inside this lib.

## License
```
Copyright 2018 Luan Gabriel
Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at 
http://www.apache.org/licenses/LICENSE-2.0 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
```
