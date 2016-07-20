### TeamCity Visual Studio Online WorkItems plugin
Enables tracking of issues defined in [Visual Studio Online projects](http://msdn.microsoft.com/en-us/library/hh409275.aspx)

----

**_Thanks for all your contributions. It's time to move to the built-in in TeamCity 10 [Team Foundation Work Items plugin](https://confluence.jetbrains.com/display/TCD10/Team+Foundation+Work+Items)._**

> :point_up: While upgrading to TeamCity 10 all issue tracker connections of this plugin will be converted into TFS Work Items, so please remove this plugin.

----

#### Functionality and Limitations

Plugin uses standard TeamCity API to integrate with Visual Studio Online. Please refer to [TeamCity documentation] (https://confluence.jetbrains.com/display/TCD8/Integrating+TeamCity+with+Issue+Tracker)

##### Master branch
- TeamCity 10.0 API compatibility
- Java 8 is required
- Build and dependency management switched to maven

##### Hajipur-9.1.x Branch
- Support for api-version 1.0
- TeamCity 9.1 API compatibility

##### Hajipur-9.0.x Branch

- Support for human readable display name on UI

##### Gaya-8.1.x Branch

- Supports authentication only with [Alternate Credentials](http://www.visualstudio.com/en-us/integrate/get-started/get-started-auth-introduction-vsi)
- Supports only one collection per issue tracker defined
- Supports only one project per issue tracker defined. Project must be specified explicitly


#### Installation and usage

Before plugin can be used, [Alternate Credentials](http://www.visualstudio.com/en-us/integrate/get-started/get-started-auth-introduction-vsi) should be enabled
Plugin can be installed by following standard [plugin installation procedure](https://confluence.jetbrains.com/display/TCD9/Installing+Additional+Plugins)

#### Compatibility

Branches define version of TeamCity, that is compatible with the plugin. When upgrading TeamCity to the next
major version, plugin branch must be switched


#### Builds on [public TeamCity server](https://teamcity.jetbrains.com/project.html?projectId=TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin&tab=projectOverview)

- [8.1.x version](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_VSO_Workitems81x&buildId=lastPinned) [![](https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VSO_Workitems81x,pinned:true/statusIcon)] (https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VSO_Workitems81x,pinned:true/statusIcon)
- [9.0.x version](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWorkIt&buildId=lastPinned) [![](https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWorkIt,pinned:true/statusIcon)] (https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWorkIt,pinned:true/statusIcon)
- [9.1.x version](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWork_2&buildId=lastPinned) [![](https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWork_2,pinned:true/statusIcon)] (https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWork_2,pinned:true/statusIcon)
- [10.0.x version](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWork_3&buildId=lastPinned) [![](https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWork_2,pinned:true/statusIcon)] (https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VisualStudioOnlineIssueTrackin_TeamCityVsOnlineWork_3,pinned:true/statusIcon)

#### Links

- [Blog post] (http://blog.jetbrains.com/teamcity/2014/11/integrating-teamcity-and-visual-studio-online-work-items/) in TeamCity blog.
- [Plugin page] (https://confluence.jetbrains.com/display/TW/Visual+Studio+Online+Work+Items)

####License
Apache 2.0



