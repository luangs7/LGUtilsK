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

