<template>
  <Teleport to="body">
    <div class="fixed top-4 right-4 z-[100] flex flex-col gap-2">
      <TransitionGroup name="toast" tag="div">
        <div
          v-for="toast in toasts"
          :key="toast.id"
          :class="toastClasses(toast.type)"
          class="px-4 py-3 rounded-md shadow-lg border-2 border-black min-w-[300px] flex items-center gap-2"
        >
          <span v-if="toast.type === 'success'" class="text-green-600">✓</span>
          <span v-else-if="toast.type === 'error'" class="text-red-600">✕</span>
          <span v-else-if="toast.type === 'warning'" class="text-yellow-600">⚠</span>
          <span v-else class="text-blue-600">ℹ</span>
          <span class="flex-1">{{ toast.message }}</span>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<script setup>
import { useToast } from '../composables/useToast'

const { toasts } = useToast()

const toastClasses = (type) => {
  const base = 'bg-white'
  const types = {
    success: 'border-green-500',
    error: 'border-red-500',
    warning: 'border-yellow-500',
    info: 'border-blue-500'
  }
  return `${base} ${types[type] || types.info}`
}
</script>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%);
}
</style>
