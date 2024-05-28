# ContactsApp

ContactsApp is an Android app that manages a list of contacts. It allows users to add, update, delete, and call contacts. The app also supports image insertion for each contact.

## Features

- Add new contacts with name, number, and optional image.
- Update existing contacts.
- Delete contacts with a swipe gesture.
- Call contacts directly from the app.
- Displays contacts in a RecyclerView.

## Activities

1. **MainActivity**: Displays the list of contacts and allows swipe actions to delete or update contacts.
2. **SaveContactsActivity**: Screen to add a new contact.
3. **UpdateContactsActivity**: Screen to update an existing contact.

## Components

### MainActivity

- Displays a list of contacts using RecyclerView.
- Supports swipe actions for deleting and updating contacts.
- Uses `ItemTouchHelper` for swipe functionality.
- Requires CALL_PHONE permission for making calls.

### SaveContactsActivity

- Provides UI for adding a new contact.
- Allows selecting an image for the contact.
- Validates input fields before saving the contact.
- Inserts contact data into the SQLite database.

### UpdateContactsActivity

- Provides UI for updating an existing contact.
- Pre-fills existing contact details.
- Updates contact data in the SQLite database.

### MyAdapter

- Adapter for RecyclerView in MainActivity.
- Binds contact data to RecyclerView items.
- Handles click events for making calls with a confirmation dialog.

### DBHelper

- SQLite database helper class.
- Manages database creation, version management, and CRUD operations.

## Database Schema

- **contacts** table with columns: 
  - `name` (TEXT)
  - `number` (TEXT)
  - `photo` (BLOB) - Optional

## Permissions

- `CALL_PHONE`: Required for making calls from the app.

