#!/usr/bin/env groovy
import groovy.json.JsonSlurper

def getAndroidBuildsShortVersion(def appEnvironment, def project, def brand) {

    final HOCKEYAPP_STAGING_CARE_APP_ID_MAP = ['myElectrolux'       : 'cf116a138086435dbc04f1678b50cc6d',
                                               'myElectrolux-sprint': '3e1788b2e0c64d47ad85e37cbfb40918',
                                               'myAEG'              : '8c16928284044b15a50916831d69d66e']

    final HOCKEYAPP_STAGING_KITCHEN_APP_ID_MAP = ['myElectrolux': 'ffe463f1bf804d1c9ce09efbe75909b2']

    final HOCKEYAPP_PREPRODUCTION_CARE_APP_ID_MAP = ['myElectrolux': 'd358888fe5c641eb853baaef40270ebe']

    def HOCKEYAPP_APP_ID

    if (appEnvironment == "staging") {
        if (project == "care") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myElectrolux")
            } else if (brand == "myElectrolux-sprint") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myElectrolux-sprint")
            } else if (brand == "myAEG") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myAEG")
            }
        } else if (project == "kitchen") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_KITCHEN_APP_ID_MAP.get("myElectrolux")
            }
        }
    } else if (appEnvironment == "preProduction") {
        if (project == "care") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_PREPRODUCTION_CARE_APP_ID_MAP.get("myElectrolux-staging")
            }
        }
    }

    return getShortVersionListFromHockeyApp(HOCKEYAPP_APP_ID)
}

def getIosBuildsShortVersion(def appEnvironment, def project, def brand) {
    final HOCKEYAPP_STAGING_CARE_APP_ID_MAP = ['myElectrolux'       : '0cfa3cf8d64c440da846ae54af06fd18',
                                               'myElectrolux-sprint': '0f11a2c395724273ae02c0f3ab37d30a',
                                               'myAEG'              : 'd7d1742b85cb4df2b85851fcbbf93232',
                                               'myAEG-sprint'       : 'f676d5d0c104444caad600134fc34c6a']

    final HOCKEYAPP_STAGING_KITCHEN_APP_ID_MAP = ['myElectrolux': 'ffe463f1bf804d1c9ce09efbe75909b2']

    final HOCKEYAPP_PREPRODUCTION_CARE_APP_ID_MAP = ['myelectrolux': 'd358888fe5c641eb853baaef40270ebe']

    def HOCKEYAPP_APP_ID

    if (appEnvironment == "staging") {
        if (project == "care") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myElectrolux")
            } else if (brand == "myElectrolux-sprint") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myElectrolux-sprint")
            }else if (brand == "myAEG") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myAEG")
            }else if (brand == "myAEG-sprint") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myAEG-sprint")
            }
        } else if (project == "kitchen") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_KITCHEN_APP_ID_MAP.get("myElectrolux")
            }
        }
    } else if (appEnvironment == "preProduction") {
        if (project == "care") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_PREPRODUCTION_CARE_APP_ID_MAP.get("myelectrolux-staging")
            }
        }
    }

    return getShortVersionListFromHockeyApp(HOCKEYAPP_APP_ID)
}

def getShortVersionListFromHockeyApp(def appId) {
    def cmd = ['bash', '-c', "curl -H 'X-HockeyAppToken:f670dc1b1b05467ea48647bef0d31016' https://rink.hockeyapp.net/api/2/apps/${appId}/app_versions.json"]
    def appVersionsJson = cmd.execute().text
    JsonSlurper jsonSlurper = new JsonSlurper()
    Object result = jsonSlurper.parseText(appVersionsJson)
    def shortBuildVersions = new ArrayList()
    result.app_versions.each { app ->
        shortBuildVersions.add(app.shortversion)
    }
    def firstTenBuildVersions = shortBuildVersions.subList(0, 9).join('\n')
    return firstTenBuildVersions
}


