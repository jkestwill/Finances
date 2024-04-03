pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }


}

rootProject.name = "FinanceHelper"
include(":app")
include(":currencyExchangeApiBy")
include(":transaction-database")
include(":exchange-rate-data")
include(":currency-exchange")
include(":category-data")

//currency
project(":currencyExchangeApiBy").projectDir=File(rootDir,"currency/currencyExchangeApiBy")
project(":currency-exchange").projectDir=File(rootDir,"currency/currency-exchange")
project(":exchange-rate-data").projectDir=File(rootDir,"currency/exchange-rate-data")
//category
project(":category-data").projectDir=File(rootDir,"category/category-data")

include(":common-data")
include(":category-main")
