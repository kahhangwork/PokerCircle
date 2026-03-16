# Poker Performance Tracker — Product Requirements Document

---

## 1. Document Metadata

| Field | Details |
|---|---|
| **Product Name** | Poker Performance Tracker *(working title)* |
| **Author** | Kah Hang (Product Manager) |
| **Version** | v0.1 (Draft) |
| **Last Updated** | 01 Dec 2025 |

**Stakeholders:**
- Product (PM): Kah Hang
- Engineering: NA (solo dev)
- UX/UI: NA (solo design)
- Data/Analytics: NA

---

## 2. Executive Summary

Poker players today lack a simple, social, and analytics-driven way to track their performance across cash games and tournaments. Many players rely on manual note-taking or spreadsheets, resulting in incomplete data and poor long-term insights.

This product offers a modern web app for users to log poker sessions, track winnings/losses, and gain performance insights. The unique value proposition is a **group feature** that allows players to create private poker groups for regular house games for more in-depth tracking of the player's stats within the private poker group.

The initial target market is Singapore's poker community (home games + casino players), with potential expansion to global users.

---

## 3. Goals & Non-Goals

### 3.1 Goals

- Allow users to easily record session results (date, duration, game type, stakes, location, profit/loss).
- Provide users with personal analytics: win rate, hourly rate, graph of performance over time.
- Enable users to create and join poker groups.
- Enable group members to view shared results and compare performance.
- Enable group members to look at stats pertaining to that poker group's sessions only.
- Build a modern, responsive web app accessible via mobile and desktop.

### 3.2 Non-Goals

- No real-money transactions or betting.
- No integration with online poker platforms (PokerStars, GGPoker, etc.) in V1.
- No social feed/live status (future phase).
- No built-in coaching or hand analysis tools for V1.
- No in-app messaging in V1.

---

## 4. Background & Context

Poker has a rapidly growing ecosystem in Southeast Asia, with more players taking the game seriously. However, tools for tracking performance are fragmented:

- Many players use Excel/Google Sheets.
- Some apps track sessions but lack community features.
- No mainstream product offers a social/competitive layer for poker statistics.

This application aims to fill that gap by combining simple session tracking with social features, appealing to casual players, aspiring grinders, and home-game groups.

---

## 5. User Personas

### Persona 1 — Casual Weekend Player *(Primary)*
- Plays 1–3 times per week.
- Wants to know if they're profitable over time.
- Wants easy, fast session logging.
- Likes comparing results with friends.

### Persona 2 — Serious Grinder
- Plays 10–30 hours per week.
- Tracks detailed performance metrics.
- Wants advanced analytics and trends.
- Likely to create or join multiple poker groups.

### Persona 3 — Home Game Host
- Regularly organises private games.
- Wants a central place where friends' results are transparent.
- Wants a central place where private game results are tracked only within the private game itself.
- Tracks how each player performs across sessions.

---

## 6. User Stories & Use Cases

### 6.1 Core User Stories

- As a user, I want to log my poker session easily so I can track my results.
- As a user, I want to see my lifetime profit/loss.
- As a user, I want graphs showing performance over time.
- As a user, I want to create a poker group and invite friends.
- As a user, I want to view all members' statistics in the group.
- As a user, I want to record the results of each session within the poker group to know how each person is doing playing within the group over time.
- As a user, I want to choose whether my results are public or private to others.

### 6.2 Group Use Cases

- Create a group
- Join a group via invite link or code
- View group leaderboard
- View group aggregated stats (total winnings, average session duration, etc.)
- Group aggregated stats for all sessions the player played (including games not within the poker group)
- Group aggregated stats for sessions the player played (only for games within the poker group)

### 6.3 Edge Cases

- User joins group but has no sessions logged
- Not all members of the group will attend every poker session → need a null input for sessions for members who didn't join the session
- User logs incomplete session (duration missing)
- Group with only one member → auto-hide leaderboard
- Leaving a group should retain historical stats *(decision required)*

---

## 7. Product Requirements

### 7.1 Functional Specifications

#### A. Account & Profile

- Users can create an account using email/password or Google login.
- Users can edit their profile (name, display picture optional, preferred currency).
- Users can toggle privacy mode:
  - **Fully public:** Everyone will be able to see the user's stats
  - **Public within group:** Only members within the group will be able to see the user's stats
  - **Private:** Only share appearance in leaderboard (no numbers)

#### B. Session Logging (Personal Sessions)

This includes any poker game played, regardless of location.

User can log:
- Game type (cash or tournament)
- Stakes (e.g., $1/$2, $2/$5)
- Location (casino name, home game, others)
- Date/time start
- End time or duration
- Buy-in
- Cash-out
- Net result (auto-calculated)
- Notes (optional)

**Acceptance Criteria:**
- Session can be added within 30 seconds
- Net profit auto-calculated
- Session post-save triggers recalculation of personal analytics
- Session can be edited or deleted

#### C. Group Sessions (Private Poker Group Results)

This is a separate logging flow from personal sessions.

**Core concept:** Home game hosts will log group sessions, and assign each player's result for that specific night.

##### C.1 Logging a Group Session

A group session includes:
- Group name (pre-selected)
- Date of game
- Stakes
- Duration
- List of members in the group
- For each member: Present? Yes/No, Buy-in, Cash-out, Net result
- Total session profit/loss validation (all net results must sum to 0)

##### C.2 Members Not Present

- Members can be toggled "Absent"
- Absent members have no data stored for that session

##### C.3 Data Linking

Each group session:
- Does **NOT** affect the player's personal lifetime stats unless the player chooses "Add this as a personal session".
- Always affects group-specific stats.

#### D. Personal Analytics

Personal analytics aggregate all personal sessions + any group sessions the user chooses to convert into personal sessions.

Must include:
- Lifetime profit/loss
- Total sessions
- Win rate (cash game hourly, tournament ROI)
- Average session length

Graphs:
- Profit over time (line chart)
- Hourly rate over time
- Histogram of results

#### E. Group Analytics (Group-Specific Stats Only)

Each group will have two layers of stats:

##### E1. Group-Level Analytics
- Total sessions recorded in the group
- Total money exchanged
- Frequency of games
- Most active members
- Group-level stakes distribution

##### E2. Member-Level Group Stats

For each member within this group only:
- Total net result
- Win/loss record
- Average result per session
- Biggest win / biggest loss
- Performance over time (line graph filtered for group sessions only)
- Number of sessions attended
- Hourly rate (group-specific)

##### E3. Group Leaderboard

Ranked by:
- Total profit
- Total sessions played
- Highest average win
- Best hourly rate

#### F. Group Management

##### F.1 Group Creation
- User can create a group with name + optional logo
- System generates unique invite link
- Group creator is assigned "Admin" role

##### F.2 Joining a Group
- Via invite link
- Requires account creation if not logged in

##### F.3 Roles

**Admin:**
- Edit group details
- Edit group sessions
- Remove members

**Member:**
- View stats
- Join group sessions
- Cannot edit past group session results (for fairness/integrity)

#### G. Privacy & Sharing Settings

Each user may define what group members can see:
- **Full Share:** all stats (personal + group-specific)
- **Group-Only Share:** only stats from group sessions

If the user joins a poker group, at least the Group-Only Share option will be selected. There will be no "private" option if the user joins a poker group.

**Default:** Group-Only Share

#### H. Notifications *(Later Phase, Optional)*

- Session logging reminders
- Group session scheduled reminders
- Summary of monthly performance

*Not needed for V1 but kept in PRD for future planning.*

---

### 7.2 Non-Functional Requirements

| Category | Requirement |
|---|---|
| **Performance** | Pages load within < 1 second on modern mobile browsers; graphs render within < 2 seconds |
| **Security** | All data encrypted at rest; authenticated API access only; role-based access for group admins |
| **Consistency** | Group session net total must sum to zero |
| **Reliability** | 99% uptime (leveraging hosted services like Supabase/Vercel) |
| **Scalability** | Designed to handle 20,000 monthly active users initially |
| **Privacy** | No personal financial information beyond session results; GDPR-ready architecture |
| **Internationalisation** *(future)* | Multi-currency support; timezone detection |

---

## 8. User Flows

| Flow | Steps |
|---|---|
| **1 — Log Personal Session** | Dashboard → "Add Session" → Input details → Save → Analytics update |
| **2 — Create Poker Group** | Dashboard → Groups → "Create Group" → Group Name → Get Invite Link → Share |
| **3 — Join Group** | Click Invite Link → Login/Create Account → Confirm → Join group → Land on Group Dashboard |
| **4 — Log Group Session** | Groups → Select Group → "Log Session" → Add date/stakes → Mark attendance → Fill results → Validate total = 0 → Save → Group stats update → Member stats update |
| **5 — View Group Leaderboard** | Groups → Select Group → Leaderboard Tab → Filter by stat (Profit / Hourly / Sessions Played) |
| **6 — Toggle Sharing Settings** | Profile → Privacy Settings → Choose sharing mode → Changes immediately reflected in all group views |

---

## 9. UX/UI Requirements

### General Design
- Mobile-first responsive approach
- Clean, minimal UI inspired by: Strava, Notion, Robinhood-style cards

### Key Screens
- Login / Sign up
- Dashboard (Personal Stats)
- Add Personal Session
- Groups Page
- Group Dashboard
- Group Leaderboard
- Group Session Entry Form
- Profile + Privacy Settings

### Visualisations
- Profit line chart
- Histogram of session results
- Group leaderboard table
- Group performance chart

### Colour Rules
- Profit numbers: **Green**
- Loss numbers: **Red**
- Neutral values: **Grey**
- Group admin badges: **Blue**

---

## 10. Technical Requirements

### Architecture Overview *(TBC)*

| Layer | Technology |
|---|---|
| Frontend | Next.js (React) |
| Backend | Next.js API routes / Node.js server |
| Database | PostgreSQL (Supabase or Railway) |
| Auth | Supabase Auth |
| Hosting | Vercel |

### High-Level Data Model

**Users**
- `id`, `name`, `email`, `profileImageUrl`, `privacySetting`

**Sessions (Personal)**
- `id`, `user_id`, `buy_in`, `cash_out`, `net_result`, `date`, `duration`, `location`, `stakes`, `notes`

**Groups**
- `id`, `name`, `created_by`, `invite_code`

**GroupMembers**
- `id`, `group_id`, `user_id`, `role` (admin/member)

**GroupSessions**
- `id`, `group_id`, `date`, `stakes`, `duration`

**GroupSessionResults**
- `id`, `group_session_id`, `user_id`, `buy_in`, `cash_out`, `net_result`, `present` (boolean)

---

## 11. Analytics & Success Metrics

### V1 KPIs

| Metric | Target |
|---|---|
| Users registered (first 3 months) | 100 |
| Weekly active users | 30% |
| Users logging ≥ 1 session/week | 60% |
| Poker groups created | 50 |
| Avg. logs per user per month | 6 |

### Group Feature KPIs

| Metric | Target |
|---|---|
| Groups logging ≥ 1 session/month | 70% |
| Group members viewing group stats weekly | 50% |

---

## 12. Launch Strategy

### Alpha Phase *(Invite Only)*
- Test with 10–20 Singapore home-game groups
- Validate group session logging
- Identify UI bottlenecks

### Beta Phase *(Soft Launch)*
- Share on SG Poker Telegram and Discord channels
- Onboard casino regulars
- Collect structured feedback

### Full Launch
- Release global version with multi-currency and language settings
- Optional: Launch on Product Hunt

---

## 13. Risks & Mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| Users hesitate to share results | Medium | Robust privacy settings |
| Incorrect group results entry | High | Add "total must = 0" validation |
| Players argue about session accuracy | Medium | Only admins can edit results |
| Regulatory perception | Low | Clear disclaimer: tracking tool only |
| Feature creep | High | Strict MVP scope |

---

## 14. Open Questions

1. Should group admins be able to delete a group session?
2. Should members be able to "challenge" incorrect session entries?
3. Should group-specific stats be exportable?
4. Should personal and group sessions merge automatically?
5. Should we allow multiple admins per group?

---

## 15. Wireframes

*To be added.*
