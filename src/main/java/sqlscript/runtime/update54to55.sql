#
# Update script db version 52 to db version 53
# Generate a table for the montly throughput of the system
# $Author: heller $
# $Revision: 1.2 $
#
#IP length is sometimes > 45 chars
#
TRUNCATE TABLE highavail
ALTER TABLE highavail DROP COLUMN localip
ALTER TABLE highavail DROP COLUMN publicip
ALTER TABLE highavail ADD COLUMN localip VARCHAR(255) NOT NULL
ALTER TABLE highavail ADD COLUMN publicip VARCHAR(255)
