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


