# mendelson-as2
This is an unofficial Mendelson AS2 Community Server mirror that tracks the [official upstream SourceForge files](https://sourceforge.net/projects/mec-as2/files/). The [upstream](https://github.com/sarog/mec-as2/tree/upstream) branch are for original upstream files (with encoding fixes) while the [dev](https://github.com/sarog/mec-as2/tree/dev) branch is for active development.

The project can be built using maven. A few formatting fixes and minor patches have been applied to the source, but overall the goal of this repo is to welcome additional improvements to the project.

Project Roadmap:

- Building of WAR artifacts
- Jetty bundling / deployment scripts
- Update dependencies to modern versions
- Find and fix security issues (there's a few severe ones!)
- Applying best practices when it comes to Java programming
- Formatting and typo fixes
- Add some crucial documentation that's sorely missing in the community version
- Create a Windows service wrapper
- External database support (e.g. MariaDB)
