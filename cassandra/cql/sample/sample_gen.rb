#! /usr/bin/ruby
require 'securerandom'
require 'date'

# SOURCE '/Users/angelica/Work/kotlin/gameforum/cassandra/cql/sample/sample_data_insert.cql';
# 全削除: TRUNCATE posts;
# Insert文出力のためのクラス
class InsertStatement
  def initialize(post_data)
    @post_data = post_data
  end

  def gen()
    keys = 'uuid, write_day, game_id, server, title, player_name, purpose, vc_use, device, comment, created_at, user_data, tags, self_tags, play_time, delete_key, image_path'
    "INSERT INTO forum.posts(#{keys}) VALUES (uuid(), #{@post_data.to_text});\n"
  end
end

# Insert文の中身を作るためのクラス
# key順が暗黙的に一致しないといけない
class PostData
  def initialize(game_id, server, title, player_name, purpose, vc_use, device, comment, created_at, user_data, tags, self_tags, play_time, delete_key, image_path)
    @game_id = game_id
    @server = server
    @title = title
    @player_name = player_name
    @purpose = purpose
    @vc_use = vc_use
    @device = device
    @comment = comment
    @created_at = created_at
    @user_data = user_data
    @tags = tags
    @self_tags = self_tags
    @play_time = play_time
    @delete_key = delete_key
    @image_path = image_path

    @write_day = created_at.strftime('%Y-%m-%d')
  end

  def to_text()
    "'#{@write_day}', '#{@game_id}', '#{@server}', '#{@title}','#{@player_name}', '#{@purpose}', '#{@vc_use}', '#{@device}', '#{@comment}', '#{@created_at}'," +
      "{ 'ip_addr': '#{@user_data[:ip_addr]}', 'user_agent': '#{@user_data[:user_agent]}' }, [#{@tags.map { |s| @tags.last == s ? "'#{s}'" : "'#{s}', " }.join }]," +
      "[#{@self_tags.map { |s| @self_tags.last == s ? "'#{s}'" : "'#{s}', " }.join }], [#{@play_time.map { |s| @play_time.last == s ? "'#{s}'" : "'#{s}', " }.join }], '#{@delete_key}'," +
      "'#{@image_path}'"
  end

  class << self
    def generate(index)
      game_id = ['pso2', 'pso2ngs', 'genshin', 'ff14'].sample
      server = ['hoge', 'fuga', 'Asia', 'Kansai'].sample
      title = SecureRandom.hex(25)
      player_name = SecureRandom.hex(8)
      purpose = ['PLAY','TEAM_LANCH','TEAM_SCOUT', 'TEAM_JOIN', 'EVENT'].sample
      vc_use = ['USE', 'UNUSE', 'EITHER'].sample
      device = ['PC', 'PS5, PC', 'PS4'].sample
      comment = SecureRandom.hex(50)
      # index*59minずつ進んでいく
      created_at = DateTime.new(2021, 6, 1, 10, 0, 0, "+09:00") + (Rational(1, 24 * 60) * (index * 59))
      user_data = {
        'ip_addr': ['10.10.10.10', '1.2.3.4', '127.0.0.1', '0.0.0.0','12.34.56.99'].sample,
        'user_agent': ['ie','edge','safari','chrome'].sample
      }
      tmpTags = (0..4).map do |i|
        ['PS_EX', 'PS_JOY', 'PS_EASY',
         'TIME_SOCIETY', 'TIME_STUDENT',
         'YEARS_10', 'YEARS_20', 'YEARS_OV_30',
         'PLAY_EASY', 'PLAY_VETERAN', 'PLAY_HERO'].sample
      end
      tags = tmpTags.uniq

      tmpTags = (0..4).map do |i|
        ['PS_EX', 'PS_JOY', 'PS_EASY',
         'TIME_SOCIETY', 'TIME_STUDENT',
         'YEARS_10', 'YEARS_20', 'YEARS_OV_30',
         'PLAY_EASY', 'PLAY_VETERAN', 'PLAY_HERO'].sample
      end
      self_tags = tmpTags.uniq

      tmpPlayTime = (0..4).map do |i|
        ['WEEKDAYS_M','WEEKDAYS_L','WEEKDAYS_N','WEEKDAYS_MN','WEEKDAYS_EM',
          'HOLIDAYS_EM','HOLIDAYS_M','HOLIDAYS_L','HOLIDAYS_N','HOLIDAYS_MN',
          'BEST_EFFORT','RANDOM','WEEKDAYS','HOLIDAYS'
        ].sample
      end
      play_time = tmpPlayTime.uniq

      PostData.new(game_id, server, title, player_name, purpose, vc_use, device, comment, created_at, user_data, tags, self_tags, play_time, 'delete', '')
    end
  end
end

File.open('./sample_data_insert.cql', mode='w') do |f|
  (0..10000).each do |index|
    post_data = PostData.generate(index)
    state = InsertStatement.new(post_data)
    f.write(state.gen)
  end
end