### TeamCity Visual Studio Online WorkItems plugin
##### Enables tracking of issues defined in [Visual Studio Online projects](http://msdn.microsoft.com/en-us/library/hh409275.aspx)

#### Functionality and Limitations

Plugin uses standard TeamCity API to integrate with Visual Studio Online. Please refer to [TeamCity documentation] (https://confluence.jetbrains.com/display/TCD8/Integrating+TeamCity+with+Issue+Tracker)

##### Gaya-8.1.x Branch

- Supports authentication only with [Alternate Credentials](http://www.visualstudio.com/en-us/integrate/get-started/get-started-auth-introduction-vsi)
- Supports only one collection per issue tracker defined
- Supports only one project per issue tracker defined. Project must be specified explicitly

#### Installation and usage

Before plugin can be used, [Alternate Credentials](http://www.visualstudio.com/en-us/integrate/get-started/get-started-auth-introduction-vsi) should be enabled
Plugin can be installed by following standard [plugin installation procedure](https://confluence.jetbrains.com/display/TCD8/Installing+Additional+Plugins)

#### Compatibility

Branches define version of TeamCity, that is compatible with the plugin. When upgrading TeamCity to the next
major version, plugin branch must be switched


#### Builds on [public TeamCity server](http://teamcity.jetbrains.com)

- [8.1.x version](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_VSO_Workitems81x&buildId=lastPinned) [![](https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VSO_Workitems81x,pinned:true/statusIcon)] (https://teamcity.jetbrains.com/app/rest/builds/buildType:TeamCityPluginsByJetBrains_VSO_Workitems81x,pinned:true/statusIcon)

#### Links

- [Blog post] (http://blog.jetbrains.com/teamcity/2014/11/integrating-teamcity-and-visual-studio-online-work-items/) in TeamCity blog.
- [Plugin page] (https://confluence.jetbrains.com/display/TW/Visual+Studio+Online+Work+Items)

####License
Apache 2.0



