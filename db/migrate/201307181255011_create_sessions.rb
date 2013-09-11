class CreateSessions < ActiveRecord::Migration
	def self.up		
		
      create_table "sessions", :force => true do |t|
        t.string   "session_id"
        t.text     "data"
        t.datetime "updated_at"
      end

      add_index "sessions", ["session_id"], :name => "sessions_session_id_index"

	end

	def self.down
			drop_table "sessions"
	end
end
