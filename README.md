# AndroidBaseApplication
Base reference for new Android projects

[![Build Status](https://travis-ci.org/tecruz/AndroidBaseApplication.svg?branch=master)](https://travis-ci.org/tecruz/AndroidBaseApplication)
[![codecov.io](https://codecov.io/github/tecruz/AndroidBaseApplication/branch/master/graph/badge.svg)](https://codecov.io/gh/tecruz/AndroidBaseApplication)

![Alt text](screen.png?raw=true)

Libraries and tools included:

- Support libraries
- RecyclerViews and CardViews 
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [Realm](https://realm.io/)
- [Butterknife](https://github.com/JakeWharton/butterknife)
- [Timber](https://github.com/JakeWharton/timber)
- [Glide](https://github.com/bumptech/glide)
- [LeakCanary](https://github.com/square/leakcanary)
- [Checkstyle](http://checkstyle.sourceforge.net/), [PMD](https://pmd.github.io/) and [Findbugs](http://findbugs.sourceforge.net/) for code analysis

### The check task

To ensure that your code is valid and stable use check: 

```
./gradlew check
