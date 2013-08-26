## YAML Template.
---
production:
  user.management.url: http://localhost:3004
  locale: en
  village: Mtema
  gvh: Mphonde
  ta: Thandazya

development:
  user.management.url: http://localhost:3004
  locale: en
  village: Mtema
  gvh: Mphonde
  ta: Thandazya

test: &TEST
  user.management.url: http://localhost:3004
  locale: ny
  village: Mtema
  gvh: Mphonde
  ta: Thandazya
  
dde_mode: vh
  
dde_ta:
  username: admin
  password: admin
  target_server: localhost:3002
  site_code: 009
  batch_count: 10
  
dde_gvh:
  username: admin
  password: test
  target_server: localhost:3003
  site_code: 009
  batch_count: 10
  gvh: Mphonde
  
dde_vh:
  username: admin
  password: admin
  target_server: localhost:3003
  site_code: 009
  batch_count: 20
  gvh: Mphonde
  vh: Thandazya
