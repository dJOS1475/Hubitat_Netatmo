# Netatmo (Connect) - Weather Station

Netatmo Integration for Hubitat.

A parent app that connects to your Netatmo account, discovers your weather station and its modules, and creates a Hubitat device for each one. The app handles all authentication, polling and unit conversion; the drivers simply present the data to dashboards and rules.

## Supported devices

| Netatmo module | Hubitat driver |
| --- | --- |
| Base station (NAMain) | Netatmo Basestation |
| Outdoor module (NAModule1) | Netatmo Outdoor Module |
| Wind gauge (NAModule2) | Netatmo Wind |
| Rain gauge (NAModule3) | Netatmo Rain |
| Additional indoor module (NAModule4) | Netatmo Additional Module |

## Installation

The easiest way to install is via [Hubitat Package Manager](https://community.hubitat.com/t/release-hubitat-package-manager-hpm-hubitatcommunity/94471) - search for **Netatmo (Connect) - Weather Station**.

To install manually, import the app and all five drivers using the raw GitHub URLs in each file, then:

1. Create an application at [dev.netatmo.com](https://dev.netatmo.com/) to obtain a **Client ID** and **Client Secret**.
2. Add the **Netatmo (Connect)** app in Hubitat and enter those credentials.
3. Click **Connect to netatmo** - this opens the Netatmo authorisation page in a new browser tab.
4. Once authorised, return to the app and select the devices you want to create.

## Configuration

All settings live in the parent app, not on the individual devices:

| Setting | Description |
| --- | --- |
| Rain / Pressure / Wind units | mm or in, mbar or inHg, kph / ms / mph / kts |
| Time format | 12 or 24 hour |
| Polling interval | 5, 10 or 15 minutes (Netatmo itself updates roughly every 10 minutes) |
| Base station offline threshold | Minutes without new data before the base station is marked `not present` (default 30) |
| Sound sensor threshold | dB level above which sound is marked as detected (default 50) |
| Reverse wind angle | Use the Netatmo display convention, where the arrow points to the source of the wind |
| Debug logging | Verbose logging for troubleshooting |

## Dashboard tiles

Every device exposes a `Summary` attribute containing a pre-formatted HTML tile, and the base station additionally exposes an `Overview` attribute that combines indoor, outdoor, wind and rain readings from the whole station. Add either as an **Attribute** tile on a dashboard.

Alongside the usual measurements, the devices also report diagnostics: `presence` (online / offline), `battery`, `battery_vp`, `wifi_status` (base) or `rf_status` (modules), `firmware`, `last_seen`, and `dataAge` on the base station.

## Change log

* **v1.7.3**
  * Bug fix: a module that reports no data (offline, flat battery, or a favourited station) no longer throws a NullPointerException that aborts the entire poll. The combined-tile cross-feed now checks that a module actually has data before reading from it, so the remaining devices keep updating
  * Bug fix: `refreshToken()` never returned true, so the retry-after-token-refresh path in `apiGet()` was dead code and never ran. It now returns true, rebuilds the query with the new token (the retry previously re-sent the expired one), and is wrapped in its own try/catch
  * Token refresh is now only attempted on 401/403 auth failures. Transient problems (DNS, 500, 503) are logged as warnings and left for the next scheduled poll
* **v1.7.2**
  * Base station presence is now staleness-based: it flips to `not present` when no new data has been received for longer than the configurable offline threshold (default 30 minutes). Also added a numeric `dataAge` attribute
* **v1.7.1**
  * Signal strength (WiFi/RF) shown as Good/Average/Poor bands instead of raw values, and moved onto the last (Battery) row in the Summary tiles
  * Overview tile no longer prints "null" for Outdoor/Wind/Rain when the base station has no such module feeding it
  * Additional device attributes now available: presence (online/offline), firmware and last_seen (all devices); wifi_status (base); rf_status and battery_vp (modules); AbsolutePressure (base); max_wind_angle (wind)
* **v1.7.0**
  * Bug fix: use a dedicated `state.authToken` for the Netatmo API token so it no longer collides with the Hubitat OAuth token (`state.accessToken`)
  * Security fix: removed client_secret from the OAuth authorize URL (it is only used server-side during token exchange)
  * Removed unused `state.response` (full API payload was stored every poll but never read) to reduce state churn
  * "Connect to netatmo" button now opens the Netatmo authentication page in a new browser tab
  * Hardened OAuth token parsing to handle both a normally-parsed map and the legacy key-as-JSON-string response form
  * Removed dead/non-functional auth block (and unused `parseAuthResponse`) from `oauthInitUrl()`
  * Replaced invalid `sendPush()` call with `log.warn` in `checkloc()`
  * Redacted client_secret/credentials from debug logs during token exchange
  * Cleanup: removed unused DecimalFormat import and unused `debugEvent()`; UTF-8 charset on `toQueryString`; simplified redundant map merges; added `command "poll"` to all drivers for consistency
  * Added user-selectable polling interval (5/10/15 minutes) in the app config; defaults to 5 minutes
  * Sound Sensor threshold now defaults to 50 dB and is null-safe (no longer errors when left blank)
  * Summary tiles: added Absolute pressure + WiFi (base), RF status (all modules), and Max Gust direction (wind)
* **v1.6.3** - Added support for HE v2.5.x to classify this as an Integration
* **v1.6** - Bug fixes: OAuth token parsing, stray syntax error, capability names, namespace/author update
* **v1.5** - Added a manual reauthorize option

## Support

Questions and bug reports: [Hubitat Community thread](https://community.hubitat.com/t/re-release-netatmo-connect-weather-station/97788)
