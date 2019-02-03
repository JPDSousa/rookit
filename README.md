Rookit
=========
[![Build Status](https://travis-ci.org/JPDSousa/rookit.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit)
[![](https://jitpack.io/v/JPDSousa/rookit.svg)](https://jitpack.io/#JPDSousa/rookit)

Rookit is the ultimate :notes: Music Library Manager :notes:.

Features :headphones:
------
Rookit is inspired by many other library managers, not only musically themed. However, Rookit stands out as it aims to handle common managing issues by resorting to alternative solutions, providing some brand-new and attrative features:
 - Both audio and metadata are stored through [MongoDB](https://www.mongodb.com/), providing a new organization model that solves some of the common problems created by storing data in a filesystem model, while providing advanced queriyng features and automatic scaling, among many other features inherited by MongoDB.
 - A new music data model (separate [project](https://github.com/JPDSousa/rookit-data-model)), built from root and inspired by models such as [Spotify](https://javascriptgorilla.wordpress.com/2016/08/23/spotify-database-schema/), [Soundcloud](https://developers.soundcloud.com/docs/api/reference) and [MusicBrainz](https://musicbrainz.org/doc/MusicBrainz_Database/Schema).
 - A parsing algorithm (separate [project](https://github.com/JPDSousa/rookit-parser)), crafted not only to parse music files from tags and file name (using a customizable list of formats) but also to learn from previous parse results.

Status
---

| Module |  CI  | Jitpack | Coverage |
|:------:|:----:|:-------:|:--------:|
| [MongoDB](https://github.com/JPDSousa/rookit-mongodb) | [![Build Status](https://travis-ci.org/JPDSousa/rookit-mongodb.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit-mongodb) | [![](https://jitpack.io/v/JPDSousa/rookit-mongodb.svg)](https://jitpack.io/#JPDSousa/rookit-mongodb) | [![codecov](https://codecov.io/gh/JPDSousa/rookit-mongodb/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/rookit-mongodb) | 
| [IO](https://github.com/JPDSousa/rookit-io) | [![Build Status](https://travis-ci.org/JPDSousa/rookit-io.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit-io) | [![](https://jitpack.io/v/JPDSousa/rookit-io.svg)](https://jitpack.io/#JPDSousa/rookit-io)| [![codecov](https://codecov.io/gh/JPDSousa/rookit-io/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/rookit-io) | 
| [Config](https://github.com/JPDSousa/rookit-config) | [![Build Status](https://travis-ci.org/JPDSousa/rookit-config.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit-config) | [![](https://jitpack.io/v/JPDSousa/rookit-config.svg)](https://jitpack.io/#JPDSousa/rookit-config)| [![codecov](https://codecov.io/gh/JPDSousa/rookit-config/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/rookit-config) | 
| [Failsafe](https://github.com/JPDSousa/rookit-failsafe) | [![Build Status](https://travis-ci.org/JPDSousa/rookit-failsafe.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit-failsafe) | [![](https://jitpack.io/v/JPDSousa/rookit-failsafe.svg)](https://jitpack.io/#JPDSousa/rookit-failsafe)| [![codecov](https://codecov.io/gh/JPDSousa/rookit-failsafe/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/rookit-failsafe) | 
| [Test](https://github.com/JPDSousa/rookit-test) | [![Build Status](https://travis-ci.org/JPDSousa/rookit-test.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit-test) | [![](https://jitpack.io/v/JPDSousa/rookit-test.svg)](https://jitpack.io/#JPDSousa/rookit-test) | [![codecov](https://codecov.io/gh/JPDSousa/rookit-test/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/rookit-test) |
| [Utils](https://github.com/JPDSousa/rookit-utils) | [![Build Status](https://travis-ci.org/JPDSousa/rookit-utils.svg?branch=master)](https://travis-ci.org/JPDSousa/rookit-utils) | [![](https://jitpack.io/v/JPDSousa/rookit-utils.svg)](https://jitpack.io/#JPDSousa/rookit-utils) | [![codecov](https://codecov.io/gh/JPDSousa/rookit-utils/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/rookit-utils) |
