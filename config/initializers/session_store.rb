# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_community_vitals_master_proxy_session',
  :secret      => '2484c29ceba35aa09f30135ca44f107590ee916b48333b85d42375143e59d458a955e7536c6e56bd68cf336472fa643d79c9f6aca68e9efb32a8d31db0f3a186'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
