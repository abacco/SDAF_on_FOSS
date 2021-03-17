# SDAF_on_FOSS
Software Dependability Assessment and Feedback on FOSS systems

# Purpose
The purpose of this project is to study and report the dependability 
of specific FOSS systems.
In particular, the project aims to understand how a FOSS system
is built and how its community work in order to accomplish the deploy 
of a target product.

# Metrics for the choice of the FOSS systems
Not every project can be studied,
SDAF_on_FOSS focuses itself on the projects that meet some specific requirements,
The metrics that, at the moment, have been chosen for this purpose are shown below:
- Number of Developers
- Number of Activities
- Size of The Project:
    - the size of a project is not strictly correlated to the numbers of devs or activities
    - SDAF_on_FOSS considers also LOC
        - The more LOC we have, the better
- Popularity of the DevCommunity:
    - SDAF_on_FOSS considers project from the most famous and large company like Google, Netflix, and Apache

# Assestment
In order to understand how dev communities work, 
SDAF_on_FOSS aims to keep track of what is the story behind a new proposed task/bugfix
The general work-flow can be imagined as:
- A new task is opened on a specific issue
- The core developers, seeing this last one, start a new discussion about how to reproduce the bug and how can you fix it
    without touching the source-code
    - if the bugfix proposed is really an issue they start working on it
Let's see how core devs and casual contributors work together in order to accomplish, based on the severity of the issue,
the fix.

# Feedback
The last step of SDAF_on_FOSS is the activity to aggregate every noticed work-flow.
We are going to use some utility tools that will make us easy to collect every relevant info about
a specific project such as RepoDriller.

# Metrics (detailed) of chosen projects
The projects shown below have been selected from the tranding section of github,
The projects share the same details.
Details:
- Spoken Language : English
- Language        : Java
- Data Range      : March, 2021
- Total Stars     : >= 500
    - Some people use "stars" to indicate that they like a project, other people use them as bookmarks so they can follow what's going on with the repo later.
- Forks           : >= 500

# Project List
Chosen Project at current time (17/03/2021) - work in progress -
            
- SeleniumHQ / selenium
  A browser automation framework and ecosystem.
  - Forks:  6,209
  - Stars:  20,351
  - https://github.com/SeleniumHQ/selenium.git

- Netflix / zuul
  Zuul is a gateway service that provides dynamic routing, monitoring, resiliency, security, and more.
  - Forks:  2,025
  - Stars:  10,632
  - https://github.com/Netflix/zuul.git

- Oracle / graal
  GraalVM: Run Programs Faster Anywhere
  - Forks:  1,114
  - Stars:  14,771
  - https://github.com/oracle/graal.git

- Google / tsunami-security-scanner
  Tsunami is a general purpose network security scanner with an extensible plugin system for detecting high severity vulnerabilities with high confidence.
  - Forks:  718
  - Stars:  6,854
  - https://github.com/oracle/graal.git

- Azure / azure-sdk-for-java
  This repository is for active development of the Azure SDK for Java. For consumers of the SDK we recommend visiting our public developer docs at https://docs.microsoft.com/en-us/java/azure/ or our versioned developer docs at https://azure.github.io/azure-sdk-for-java.
  - Forks: 959
  - Stars: 830
  - https://github.com/Azure/azure-sdk-for-java.git

- Shopizer-ecommerce / shopizer
  Shopizer java e-commerce software
  - Forks: 1,913
  - Stars: 1,991
  - https://github.com/shopizer-ecommerce/shopizer.git

