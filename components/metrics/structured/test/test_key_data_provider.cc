// Copyright 2023 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#include "components/metrics/structured/test/test_key_data_provider.h"

#include "base/check.h"
#include "base/time/time.h"
#include "components/metrics/structured/key_data_provider_file.h"
#include "components/metrics/structured/structured_metrics_validator.h"

namespace metrics::structured {

TestKeyDataProvider::TestKeyDataProvider(const base::FilePath& device_key_path)
    : TestKeyDataProvider(device_key_path, base::FilePath()) {}

TestKeyDataProvider::TestKeyDataProvider(const base::FilePath& device_key_path,
                                         const base::FilePath& profile_key_path)
    : device_key_path_(device_key_path), profile_key_path_(profile_key_path) {
  device_key_data_ = std::make_unique<KeyDataProviderFile>(
      device_key_path_, base::Milliseconds(0));
  device_key_data_->AddObserver(this);
}

TestKeyDataProvider::~TestKeyDataProvider() {
  device_key_data_->RemoveObserver(this);
  if (profile_key_data_) {
    profile_key_data_->RemoveObserver(this);
  }
}

absl::optional<uint64_t> TestKeyDataProvider::GetId(
    const std::string& project_name) {
  // Validates the event. If valid, retrieve the metadata associated
  // with the event.
  auto maybe_project_validator =
      validator::Validators::Get()->GetProjectValidator(project_name);
  const auto* project_validator = maybe_project_validator.value();

  switch (project_validator->id_scope()) {
    case IdScope::kPerProfile: {
      if (profile_key_data_) {
        return profile_key_data_->GetId(project_name);
      }
      break;
    }
    case IdScope::kPerDevice: {
      if (project_validator->event_type() ==
          StructuredEventProto_EventType_SEQUENCE) {
        if (!profile_key_data_) {
          return absl::nullopt;
        }
        return profile_key_data_->GetId(project_name);
      } else if (device_key_data_) {
        return device_key_data_->GetId(project_name);
      }
      break;
    }
    default:
      NOTREACHED();
      break;
  }
  return absl::nullopt;
}

absl::optional<uint64_t> TestKeyDataProvider::GetSecondaryId(
    const std::string& project_name) {
  auto maybe_project_validator =
      validator::Validators::Get()->GetProjectValidator(project_name);
  if (!maybe_project_validator.has_value()) {
    return absl::nullopt;
  }

  const auto* project_validator = maybe_project_validator.value();

  // Only SEQUENCE types have secondary ids.
  if (project_validator->event_type() !=
      StructuredEventProto_EventType_SEQUENCE) {
    return absl::nullopt;
  }

  DCHECK(device_key_data_);
  if (device_key_data_ && device_key_data_->IsReady()) {
    return device_key_data_->GetId(project_name);
  }

  return absl::nullopt;
}

KeyData* TestKeyDataProvider::GetKeyData(const std::string& project_name) {
  // Validates the event. If valid, retrieve the metadata associated
  // with the event.
  auto maybe_project_validator =
      validator::Validators::Get()->GetProjectValidator(project_name);
  if (!maybe_project_validator.has_value()) {
    return nullptr;
  }
  const auto* project_validator = maybe_project_validator.value();

  switch (project_validator->id_scope()) {
    case IdScope::kPerProfile: {
      if (profile_key_data_) {
        return profile_key_data_->GetKeyData(project_name);
      }
      break;
    }
    case IdScope::kPerDevice: {
      if (project_validator->event_type() ==
          StructuredEventProto_EventType_SEQUENCE) {
        if (!profile_key_data_) {
          return nullptr;
        }
        return profile_key_data_->GetKeyData(project_name);
      } else if (device_key_data_) {
        return device_key_data_->GetKeyData(project_name);
      }
      break;
    }
    default:
      NOTREACHED();
      break;
  }

  return nullptr;
}

bool TestKeyDataProvider::IsReady() {
  return device_key_data_->IsReady();
}

void TestKeyDataProvider::OnProfileAdded(const base::FilePath& profile_path) {
  // If the profile path has not been set, then set it here.
  if (profile_key_path_.empty()) {
    profile_key_path_ = profile_path;
  }

  DCHECK(!profile_key_path_.empty());

  profile_key_data_ = std::make_unique<KeyDataProviderFile>(
      profile_key_path_, base::Milliseconds(0));
  profile_key_data_->AddObserver(this);
}

void TestKeyDataProvider::Purge() {
  if (profile_key_data_->IsReady()) {
    profile_key_data_->Purge();
  }

  if (device_key_data_->IsReady()) {
    device_key_data_->Purge();
  }
}

void TestKeyDataProvider::OnKeyReady() {
  // Notifies observers both when device and profile keys are ready.
  NotifyKeyReady();
}

}  // namespace metrics::structured
