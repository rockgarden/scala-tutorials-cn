lazy val playCaching = (project in file("caching-in-play"))
lazy val configAccess = (project in file("configuration-access"))
lazy val erroHandling = (project in file("custom-error-handling"))
lazy val asyncTasks = (project in file("async-tasks"))
lazy val playintro = (project in file("introduction-to-play"))
lazy val playTemplates = (project in file("play-templates"))
lazy val di = (project in file("dependency-injection"))
lazy val resApi = (project in file("rest-api"))
lazy val appTests = (project in file("application-tests"))
  .settings(Defaults.itSettings)
  .configs(IntegrationTest)
