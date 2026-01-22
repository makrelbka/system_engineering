import { ref } from 'vue'

const toasts = ref([])

export function useToast() {
  const showToast = (message, type = 'success') => {
    const id = Date.now()
    const toast = {
      id,
      message,
      type
    }

    toasts.value.push(toast)

    setTimeout(() => {
      const index = toasts.value.findIndex(t => t.id === id)
      if (index > -1) {
        toasts.value.splice(index, 1)
      }
    }, 3000)
  }

  const toast = {
    success: (message) => showToast(message, 'success'),
    error: (message) => showToast(message, 'error'),
    info: (message) => showToast(message, 'info'),
    warning: (message) => showToast(message, 'warning')
  }

  return {
    toasts,
    toast
  }
}
