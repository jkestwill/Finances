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
include(":core:transaction-database")
include(":core:currency:currencyExchangeApiBy")
include(":core:currency:exchange-rate-data")
include(":core:currency:currency-exchange")
include(":core:category:category-data")

//currency
//project(":currencyExchangeApiBy").projectDir=File(rootDir,"currency/currencyExchangeApiBy")
//project(":currency-exchange").projectDir=File(rootDir,"currency/currency-exchange")
//project(":exchange-rate-data").projectDir=File(rootDir,"currency/exchange-rate-data")
////category
//project("core:category:category-data").projectDir=File(rootDir,"core/category/category-data")

include(":core:common-data")
include(":features:category:category-main")
include(":core:transaction:transaction-data")
